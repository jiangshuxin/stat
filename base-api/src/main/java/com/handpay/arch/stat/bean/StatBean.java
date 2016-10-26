package com.handpay.arch.stat.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class StatBean<T,R> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 主题(kafka)
	 */
	private String topic;
	/**
	 * 结果主题(kafka)
	 */
	private String resultTopic;
	/**
	 * 批次间隔
	 */
	private Long batchDuration;
	/**
	 * 窗口间隔
	 */
	private Long windowDuration;
	/**
	 * 滑动间隔
	 */
	private Long slideDuration;
	/**
	 * 输入类型
	 */
	private Class<T> tableClass;
	/**
	 * 处理逻辑(MapReduce/sql形式)
	 */
	private String[] sql;
	/**
	 * 输出类型
	 */
	private Class<R> resultClass;
	/**
	 * 扩展属性
	 */
	private Map<String,String> propertyMap = Maps.newHashMap();
	
	//以下属性从注解获取
	/**
	 * 分组键名
	 */
	private String groupKey;
	/**
	 * 展示键名
	 */
	private List<String> valueKeyList = Lists.newArrayList();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getResultTopic() {
		return resultTopic;
	}

	public void setResultTopic(String resultTopic) {
		this.resultTopic = resultTopic;
	}

	public Long getBatchDuration() {
		return batchDuration;
	}

	public void setBatchDuration(Long batchDuration) {
		this.batchDuration = batchDuration;
	}

	public Long getWindowDuration() {
		return windowDuration;
	}

	public void setWindowDuration(Long windowDuration) {
		this.windowDuration = windowDuration;
	}

	public Long getSlideDuration() {
		return slideDuration;
	}

	public void setSlideDuration(Long slideDuration) {
		this.slideDuration = slideDuration;
	}

	public Class<T> getTableClass() {
		return tableClass;
	}

	public void setTableClass(Class<T> tableClass) {
		this.tableClass = tableClass;
	}

	public String[] getSql() {
		return sql;
	}

	public void setSql(String[] sql) {
		this.sql = sql;
	}

	public Class<R> getResultClass() {
		return resultClass;
	}

	public void setResultClass(Class<R> resultClass) {
		this.resultClass = resultClass;
	}

	public Map<String, String> getPropertyMap() {
		return propertyMap;
	}

	public void setPropertyMap(Map<String, String> propertyMap) {
		this.propertyMap = propertyMap;
	}

	public String getGroupKey() {
		return groupKey;
	}

	public void setGroupKey(String groupKey) {
		this.groupKey = groupKey;
	}

	public List<String> getValueKeyList() {
		return valueKeyList;
	}

	public void setValueKeyList(List<String> valueKeyList) {
		this.valueKeyList = valueKeyList;
	}
}
