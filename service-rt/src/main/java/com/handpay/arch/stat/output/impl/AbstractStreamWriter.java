package com.handpay.arch.stat.output.impl;

import java.io.Serializable;
import java.util.SortedMap;
import java.util.TreeMap;

import com.handpay.arch.stat.bean.CommonResult;
import com.handpay.arch.stat.output.StreamWriter;

public abstract class AbstractStreamWriter implements StreamWriter {

	@Override
	public void write(String statName, CommonResult... unit) {
		SaveRequest[] request = buildRequest(statName,unit);
		write(request);
	}

	protected abstract void write(SaveRequest[] request);

	private SaveRequest[] buildRequest(String statName, CommonResult[] units) {
		//yyyyMMdd分组的情况：跨日终
		SortedMap<String,SaveRequest> yyMap = new TreeMap<String,SaveRequest>();
		for(CommonResult unit : units){
			String ymd = unit.getYyyyMMdd();
			if(yyMap.containsKey(ymd)){
				SaveRequest req = yyMap.get(ymd);
				req.getTimeValueMap().put(unit.getHhmmss(), unit);
			}else{
				SaveRequest req = new SaveRequest();
				req.setStatName(statName);
				req.setYyyyMMdd(ymd);
				SortedMap<String, CommonResult> timeValueMap = new TreeMap<String, CommonResult>();
				timeValueMap.put(unit.getHhmmss(), unit);
				req.setTimeValueMap(timeValueMap);
				yyMap.put(ymd, req);
			}
		}
		return yyMap.values().toArray(new SaveRequest[0]);
	}

	public static class SaveRequest implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String statName;
		private String yyyyMMdd;
		private SortedMap<String, CommonResult> timeValueMap;

		public String getStatName() {
			return statName;
		}

		public void setStatName(String statName) {
			this.statName = statName;
		}

		public String getYyyyMMdd() {
			return yyyyMMdd;
		}

		public void setYyyyMMdd(String yyyyMMdd) {
			this.yyyyMMdd = yyyyMMdd;
		}

		public SortedMap<String, CommonResult> getTimeValueMap() {
			return timeValueMap;
		}

		public void setTimeValueMap(SortedMap<String, CommonResult> timeValueMap) {
			this.timeValueMap = timeValueMap;
		}
	}
}
