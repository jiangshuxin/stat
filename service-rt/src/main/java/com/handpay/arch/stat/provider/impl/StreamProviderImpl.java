package com.handpay.arch.stat.provider.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.SetUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.clearspring.analytics.util.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import com.handpay.arch.stat.Constants;
import com.handpay.arch.stat.bean.CommonResult;
import com.handpay.arch.stat.provider.StreamProvider;
import com.handpay.rache.core.spring.StringRedisTemplateX;

@Service("streamProvider")
public class StreamProviderImpl implements StreamProvider {
	
	private FastDateFormat dayFormat = FastDateFormat.getInstance("yyyy-MM-dd");
	
	@Autowired
	@Qualifier("stringRedisTemplateX")
	private StringRedisTemplateX stringRedisTemplateX;
	
	@Override
	public Set<String> days(String statName) {
		return stringRedisTemplateX.boundZSetOps(statName).range(0, -1);
	}

	@Override
	public List<? extends CommonResult> timeValueSet(String statName, String day) {
		Set<String> set = stringRedisTemplateX.boundZSetOps(StringUtils.join(new Object[]{statName,day}, "-")).range(0, -1);
		return convertSet(set);
	}

	private List<CommonResult> convertSet(Set<String> set) {
		List<CommonResult> newList = Lists.newArrayList();
		for(String str : set){
			newList.add(JSON.parseObject(str.toString(), CommonResult.class));
		}
		return newList;
	}

	@Override
	public List<? extends CommonResult> timeValueSet(String statName,Date fromDate,Date toDate) {
		if(fromDate.after(toDate))throw new IllegalArgumentException("fromDate is later than toDate!");
		Double fromD = null;
		Double toD = null;
		fromD = Double.parseDouble(String.valueOf(fromDate.getTime()));
		toD = Double.parseDouble(String.valueOf(toDate.getTime()));
		Duration duration = new Duration();
		duration.setFirstDay(dayFormat.format(fromDate));
		duration.setLastDay(dayFormat.format(toDate));
		if(!DateUtils.isSameDay(fromDate, toDate)){
			//fromDate尝试加一天，如果+1后小于toDate，则amongDays添加+1后的值，直到等于toDate结束
			extractDuration(fromDate, toDate, duration);
		}
		
		Set<String> set = extractResultSet(statName, fromD, toD, duration);
		return convertSet(set);
	}

	@Override
	public Map<String,Map<String,BigDecimal>> sum(String statName,String day,String groupKey,String[] columns) {
		Set<String> set = stringRedisTemplateX.boundZSetOps(StringUtils.join(new Object[]{statName,day}, "-")).range(0, -1);
		Map<String,Map<String,BigDecimal>> resultMap = extractColumnMap(set, groupKey,columns);
		return resultMap;
	}

	@Override
	public Map<String,Map<String,BigDecimal>> sum(String statName, Date fromDate, Date toDate, String groupKey,String... columns) {
		if(fromDate.after(toDate))throw new IllegalArgumentException("fromDate is later than toDate!");
		Double fromD = null;
		Double toD = null;
		fromD = Double.parseDouble(String.valueOf(fromDate.getTime()));
		toD = Double.parseDouble(String.valueOf(toDate.getTime()));
		Duration duration = new Duration();
		duration.setFirstDay(dayFormat.format(fromDate));
		duration.setLastDay(dayFormat.format(toDate));
		if(!DateUtils.isSameDay(fromDate, toDate)){
			//fromDate尝试加一天，如果+1后小于toDate，则amongDays添加+1后的值，直到等于toDate结束
			extractDuration(fromDate, toDate, duration);
		}
		Set<String> resultSet = extractResultSet(statName, fromD, toD, duration);
		return extractColumnMap(resultSet, groupKey,columns);
	}

	@Override
	public List<String> findGroupByName(String statName) {
		Set<String> groupKeySet = stringRedisTemplateX.boundZSetOps(statName + Constants.SEPARATOR_VERTICAL + Constants.REDIS_GROUP_KEY_SET).range(0, -1);
		return new ArrayList<String>(groupKeySet);
	}

	private Set<String> extractResultSet(String statName, Double fromD, Double toD, Duration duration) {
		if(StringUtils.equals(duration.getFirstDay(), duration.getLastDay())){//同一天
			return stringRedisTemplateX.boundZSetOps(StringUtils.join(new Object[]{statName,duration.getFirstDay()}, "-")).rangeByScore(fromD, toD);
		}
		Date now = new Date();
		Double nowD = Double.parseDouble(String.valueOf(now.getTime()));
		Set<String> allSet = Sets.newHashSet();
		if(StringUtils.isNotEmpty(duration.getFirstDay())){
			Set<String> firstSet = stringRedisTemplateX.boundZSetOps(StringUtils.join(new Object[]{statName,duration.getFirstDay()}, "-")).rangeByScore(fromD, nowD);//+inf
			allSet.addAll(firstSet);
		}
		for(String day : duration.getAmongDays()){
			Set<String> amongSet = stringRedisTemplateX.boundZSetOps(StringUtils.join(new Object[]{statName,day}, "-")).rangeByScore(0, nowD);
			allSet.addAll(amongSet);
		}
		if(StringUtils.isNotEmpty(duration.getLastDay())){
			Set<String> lastSet = stringRedisTemplateX.boundZSetOps(StringUtils.join(new Object[]{statName,duration.getLastDay()}, "-")).rangeByScore(0, toD);
			allSet.addAll(lastSet);
		}
		return allSet;
	}

	private Map<String,Map<String,BigDecimal>> extractColumnMap(Set<String> allSet,String groupKey ,String[] columns) {
		Map<String,Map<String,BigDecimal>> groupMap = Maps.newHashMap();
		if(StringUtils.isEmpty(groupKey)){
			Map<String,BigDecimal> resultMap = Maps.newHashMap();
			for(String c : columns){
				resultMap.put(c, BigDecimal.ZERO);
			}
			for(String jsonStr : allSet){
				JSONObject obj = JSON.parseObject(jsonStr);
				for(String c : columns){
					BigDecimal dec = obj.getBigDecimal(c);
					resultMap.put(c, resultMap.get(c).add(dec));
				}
			}
			groupMap.put("NO_GROUP_KEY", resultMap);
		}else{
			for(String jsonStr : allSet){
				JSONObject obj = JSON.parseObject(jsonStr);
				String groupValue = obj.getString(groupKey);
				Map<String,BigDecimal> resultMap = null;
				if(groupMap.containsKey(groupValue)){
					resultMap = groupMap.get(groupValue);
				}else{
					resultMap = Maps.newHashMap();
					for(String c : columns){
						resultMap.put(c, BigDecimal.ZERO);
					}
					groupMap.put(groupValue, resultMap);
				}
				for(String c : columns){
					BigDecimal dec = obj.getBigDecimal(c);
					resultMap.put(c, resultMap.get(c).add(dec));
				}
			}
		}
		return groupMap;
	}

	private void extractDuration(Date fromDate, Date toDate, Duration duration) {
		Date addOne = DateUtils.addDays(fromDate, 1);
		if(!DateUtils.isSameDay(addOne, toDate)){
			duration.getAmongDays().add(dayFormat.format(addOne));
			extractDuration(addOne, toDate, duration);
		}
	}
	
	public static class Duration implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private String firstDay;
		private List<String> amongDays = Lists.newArrayList();
		private String lastDay;
		public String getFirstDay() {
			return firstDay;
		}
		public void setFirstDay(String firstDay) {
			this.firstDay = firstDay;
		}
		public List<String> getAmongDays() {
			return amongDays;
		}
		public void setAmongDays(List<String> amongDays) {
			this.amongDays = amongDays;
		}
		public String getLastDay() {
			return lastDay;
		}
		public void setLastDay(String lastDay) {
			this.lastDay = lastDay;
		}
	}
}
