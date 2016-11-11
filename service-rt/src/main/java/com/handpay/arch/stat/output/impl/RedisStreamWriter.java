package com.handpay.arch.stat.output.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.handpay.arch.stat.bean.CommonResult;
import com.handpay.rache.core.spring.StringRedisTemplateX;
import com.handpay.rache.core.spring.connection.StringRedisConnectionX;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *StreamWriter默认实现,基于Redis
 * <ol>
 * <li>根据statName存储yyyyMMdd集合,表示该指标有哪些天的记录;</li>
 * <li>根据statName+yyyyMMdd+groupKey存储SaveRequest对象,便于历史数据查询</li>
 * <li>根据'stat-runtime-result'+statName+groupKey推送SaveRequest对象</li>
 * <li>根据statName+'GroupKeySet'存储该指标下groupKey的真实值,便于后续查找该指标下的维度信息。</li>
 * <ol/>
 */
@Service("redisStreamWriter")
public class RedisStreamWriter extends AbstractStreamWriter {
	@Autowired
	@Qualifier("stringRedisTemplateX")
	private StringRedisTemplateX stringRedisTemplateX;

	public static final String REDIS_RUNTIME_KEY = "stat-runtime-result";
	public static final String GROUP_SET_SUFFIX = "GroupKeySet";
	
	@Override
	protected void write(SaveRequest[] request) {
		for(final SaveRequest req : request){
			//历史记录存储,本集合存储日期,表示有哪天的数据
			stringRedisTemplateX.boundZSetOps(req.getStatBean().getName()).add(req.getYyyyMMdd(),Double.parseDouble(StringUtils.replace(req.getYyyyMMdd(), "-", "")));//{statName : [2016-08-08,2016-08-09]}

			final String groupKey = req.getStatBean().getGroupKey();

			//groupKey如果存在,存储明细数据的集合加上groupKey,方便根据groupKey进行查找
			if(StringUtils.isNotEmpty(groupKey)){
				final Map<String,Set<TypedTuple<String>>> map = extractMapTransform2(req,groupKey);
				final Map<String,Set<CommonResult>> map2 = extractMapTransform3(req,groupKey);
				for(String key : map.keySet()){
					stringRedisTemplateX.boundZSetOps(StringUtils.join(new Object[]{req.getStatBean().getName(),req.getYyyyMMdd(),key}, "-")).add(map.get(key));
				}

				//推送至实时消费模块
				stringRedisTemplateX.execute(new RedisCallback<Long>(){
					@Override
					public Long doInRedis(RedisConnection connection) throws DataAccessException {
						StringRedisConnectionX conn = (StringRedisConnectionX)connection;
						for(String key : map2.keySet()){
							conn.publish(StringUtils.join(new Object[]{REDIS_RUNTIME_KEY,req.getStatBean().getName(),key}, "-"), JSON.toJSONString(map2.get(key), SerializerFeature.WriteDateUseDateFormat));
						}
						return 0L;
					}});
			}else{
				final Set<TypedTuple<String>> set = extractMapTransform(req);
				stringRedisTemplateX.boundZSetOps(StringUtils.join(new Object[]{req.getStatBean().getName(),req.getYyyyMMdd()}, "-")).add(set);

				//推送至实时消费模块
				stringRedisTemplateX.execute(new RedisCallback<Long>(){
					@Override
					public Long doInRedis(RedisConnection connection) throws DataAccessException {
						StringRedisConnectionX conn = (StringRedisConnectionX)connection;
						//FIXME
						return conn.publish(StringUtils.join(new Object[]{REDIS_RUNTIME_KEY,req.getStatBean().getName()}, "-"), JSON.toJSONString(set, SerializerFeature.WriteDateUseDateFormat));
					}});
			}
		}
	}

	private Set<TypedTuple<String>> extractMapTransform(SaveRequest req) {
		Set<TypedTuple<String>> set = Sets.newHashSet();
		for(String key : req.getTimeValueMap().keySet()){
			DefaultTypedTuple<String> tt = new DefaultTypedTuple<String>(JSON.toJSONString(req.getTimeValueMap().get(key), SerializerFeature.WriteClassName,SerializerFeature.WriteDateUseDateFormat),Double.parseDouble(req.getTimeValueMap().get(key).getNowLong().toString()));
			set.add(tt);
		}
		return set;
	}

	//groupKey分两种情况,单个groupKey,或多个groupKey
	private Map<String,Set<TypedTuple<String>>> extractMapTransform2(SaveRequest req,String groupKey) {
		Map<String,Set<TypedTuple<String>>> map = Maps.newHashMap();

        String[] groupKeyArray = StringUtils.split(groupKey,'|');
		for(String key : req.getTimeValueMap().keySet()){
			CommonResult result = req.getTimeValueMap().get(key);
			List<Object> groupValueList = Lists.newArrayList();
			try {
			    for(String gkey : groupKeyArray){
                    groupValueList.add(PropertyUtils.getProperty(result,gkey));
                }
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(groupValueList.isEmpty()) continue;
			String groupKeyStr = StringUtils.join(groupValueList,'|');
			DefaultTypedTuple<String> tt = new DefaultTypedTuple<String>(JSON.toJSONString(result, SerializerFeature.WriteClassName,SerializerFeature.WriteDateUseDateFormat),Double.parseDouble(req.getTimeValueMap().get(key).getNowLong().toString()));
			if(map.containsKey(groupKeyStr)){
				map.get(groupKeyStr).add(tt);
			}else{
				Set<TypedTuple<String>> set = Sets.newHashSet();
				set.add(tt);
				map.put(groupKeyStr,set);
			}
		}
		return map;
	}

	private Map<String,Set<CommonResult>> extractMapTransform3(SaveRequest req,String groupKey) {
		Map<String,Set<CommonResult>> map = Maps.newHashMap();
		Set<TypedTuple<String>> gkSet = Sets.newHashSet();

        String[] groupKeyArray = StringUtils.split(groupKey,'|');
        for(String key : req.getTimeValueMap().keySet()){
			CommonResult result = req.getTimeValueMap().get(key);
            List<Object> groupValueList = Lists.newArrayList();
            try {
                for(String gkey : groupKeyArray){
                    groupValueList.add(PropertyUtils.getProperty(result,gkey));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(groupValueList.isEmpty()) continue;
            String groupKeyStr = StringUtils.join(groupValueList,'|');
			DefaultTypedTuple<String> tt = new DefaultTypedTuple<String>(groupKeyStr,100D);
			gkSet.add(tt);
			if(map.containsKey(groupKeyStr)){
				map.get(groupKeyStr).add(result);
			}else{
				Set<CommonResult> set = Sets.newHashSet();
				set.add(result);
				map.put(groupKeyStr,set);
			}
		}
		stringRedisTemplateX.boundZSetOps(StringUtils.join(new String[]{req.getStatBean().getName(),GROUP_SET_SUFFIX},'|')).add(gkSet);
		return map;
	}
}
