package com.handpay.arch.stat.provider;

import java.io.Serializable;
import java.util.Map;
import java.util.SortedSet;

public class StreamResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String statName;
	private SortedSet<String> keySet;
	private Map<String, Map<String, String>> resultMap;// 日期为key 时分秒为内部key

	public String getStatName() {
		return statName;
	}

	public void setStatName(String statName) {
		this.statName = statName;
	}

	public SortedSet<String> getKeySet() {
		return keySet;
	}

	public void setKeySet(SortedSet<String> keySet) {
		this.keySet = keySet;
	}

	public Map<String, Map<String, String>> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Map<String, String>> resultMap) {
		this.resultMap = resultMap;
	}
}