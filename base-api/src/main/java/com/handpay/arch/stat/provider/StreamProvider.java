package com.handpay.arch.stat.provider;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.handpay.arch.stat.bean.CommonResult;

public interface StreamProvider {

	Set<String> days(String statName);
	
	List<? extends CommonResult> timeValueSet(String statName,String day);
	List<? extends CommonResult> timeValueSet(String statName,Date fromDate,Date toDate);
	
	Map<String,Map<String,BigDecimal>> sum(String statName,String day,String groupKey,String[] columns);
	Map<String,Map<String,BigDecimal>> sum(String statName,Date fromDate,Date toDate,String groupKey,String[] columns);

	List<String> findGroupByName(String statName);
}
