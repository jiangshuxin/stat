package com.handpay.arch.stat.output.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Sets;
import com.handpay.rache.core.spring.StringRedisTemplateX;
import com.handpay.rache.core.spring.connection.StringRedisConnectionX;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("redisStreamWriter")
public class RedisStreamWriter extends AbstractStreamWriter {
	@Autowired
	@Qualifier("stringRedisTemplateX")
	private StringRedisTemplateX stringRedisTemplateX;
	
	@Override
	protected void write(SaveRequest[] request) {
		System.out.println("########################");
		for(final SaveRequest req : request){
			//历史记录存储,本集合存储日期,表示有哪天的数据
			stringRedisTemplateX.boundZSetOps(req.getStatBean().getName()).add(req.getYyyyMMdd(),Double.parseDouble(StringUtils.replace(req.getYyyyMMdd(), "-", "")));//{statName : [2016-08-08,2016-08-09]}

			final String groupKey = req.getStatBean().getGroupKey();
			Set<TypedTuple<String>> set = extractMapTransform(req);

			//groupKey如果存在,存储明细数据的集合加上groupKey,方便根据groupKey进行查找
			if(StringUtils.isNotEmpty(req.getStatBean().getGroupKey())){
				stringRedisTemplateX.boundZSetOps(StringUtils.join(new Object[]{req.getStatBean().getName(),req.getYyyyMMdd(),groupKey}, "-")).add(set);

				//推送至实时消费模块
				stringRedisTemplateX.execute(new RedisCallback<Long>(){
					@Override
					public Long doInRedis(RedisConnection connection) throws DataAccessException {
						StringRedisConnectionX conn = (StringRedisConnectionX)connection;
						return conn.publish(StringUtils.join(new Object[]{"stat-runtime-result-",req.getStatBean().getName(),groupKey}, "-"), JSON.toJSONString(req, SerializerFeature.WriteDateUseDateFormat));
					}});
			}else{
				stringRedisTemplateX.boundZSetOps(StringUtils.join(new Object[]{req.getStatBean().getName(),req.getYyyyMMdd()}, "-")).add(set);

				//推送至实时消费模块
				stringRedisTemplateX.execute(new RedisCallback<Long>(){
					@Override
					public Long doInRedis(RedisConnection connection) throws DataAccessException {
						StringRedisConnectionX conn = (StringRedisConnectionX)connection;
						return conn.publish(StringUtils.join(new Object[]{"stat-runtime-result-",req.getStatBean().getName()}, "-"), JSON.toJSONString(req, SerializerFeature.WriteDateUseDateFormat));
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
}
