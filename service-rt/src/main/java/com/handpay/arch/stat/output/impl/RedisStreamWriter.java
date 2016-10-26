package com.handpay.arch.stat.output.impl;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Sets;
import com.handpay.rache.core.spring.StringRedisTemplateX;
import com.handpay.rache.core.spring.connection.StringRedisConnectionX;

@Service("redisStreamWriter")
public class RedisStreamWriter extends AbstractStreamWriter {
	@Autowired
	@Qualifier("stringRedisTemplateX")
	private StringRedisTemplateX stringRedisTemplateX;
	
	@Override
	protected void write(SaveRequest[] request) {
		
		for(final SaveRequest req : request){
			//历史记录存储
			stringRedisTemplateX.boundZSetOps(req.getStatName()).add(req.getYyyyMMdd(),Double.parseDouble(StringUtils.replace(req.getYyyyMMdd(), "-", "")));//{statName : [2016-08-08,2016-08-09]}
			
			Set<TypedTuple<String>> set = extractMapTransform(req);
			stringRedisTemplateX.boundZSetOps(StringUtils.join(new Object[]{req.getStatName(),req.getYyyyMMdd()}, "-")).add(set);
			
			//推送至实时消费模块
			stringRedisTemplateX.execute(new RedisCallback<Long>(){
				@Override
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					StringRedisConnectionX conn = (StringRedisConnectionX)connection;
					return conn.publish("stat-runtime-result-"+req.getStatName(), JSON.toJSONString(req, SerializerFeature.WriteDateUseDateFormat));
				}});
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
