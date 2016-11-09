package com.handpay.arch.stat.output.impl;

import com.handpay.arch.stat.bean.CommonResult;
import com.handpay.arch.stat.bean.StatBean;
import com.handpay.arch.stat.output.StreamWriter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.SortedMap;
import java.util.TreeMap;

public abstract class AbstractStreamWriter implements StreamWriter {

	@Override
	public void write(StatBean statBean, CommonResult... unit) {
		System.out.println("########################");
		SaveRequest[] request = buildRequest(statBean,unit);
		write(request);
	}

	protected abstract void write(SaveRequest[] request);

	private SaveRequest[] buildRequest(StatBean statBean, CommonResult[] units) {
		//yyyyMMdd分组的情况：跨日终
		SortedMap<String,SaveRequest> yyMap = new TreeMap<String,SaveRequest>();
		for(CommonResult unit : units){
			String ymd = unit.getYyyyMMdd();
			String yyKey = ymd;
			//yyyyMMdd+groupKey分组,相当于SaveRequest按照groupKey进行分组
			if(PropertyUtils.isReadable(unit,statBean.getGroupKey())){
				Object groupKeyObj = null;
				try {
					groupKeyObj = PropertyUtils.getProperty(unit,statBean.getGroupKey());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(groupKeyObj != null) yyKey = StringUtils.join(new String[]{ymd,groupKeyObj.toString()},"-");
			}
			if(yyMap.containsKey(yyKey)){
				SaveRequest req = yyMap.get(yyKey);
				req.getTimeValueMap().put(unit.getHhmmss(), unit);
			}else{
				SaveRequest req = new SaveRequest();
				req.setStatBean(statBean);
				req.setYyyyMMdd(ymd);
				SortedMap<String, CommonResult> timeValueMap = new TreeMap<String, CommonResult>();
				timeValueMap.put(unit.getHhmmss(), unit);
				req.setTimeValueMap(timeValueMap);
				yyMap.put(yyKey, req);
			}
		}
		return yyMap.values().toArray(new SaveRequest[0]);
	}

	public static class SaveRequest implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private StatBean statBean;
		private String yyyyMMdd;
		private SortedMap<String, CommonResult> timeValueMap;

		public StatBean getStatBean() {
			return statBean;
		}

		public void setStatBean(StatBean statBean) {
			this.statBean = statBean;
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
