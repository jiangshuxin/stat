package com.handpay.arch.stat.parser.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.handpay.arch.stat.parser.StreamParser;

public abstract class SequentialParser implements StreamParser {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 存储单行解析结果的链表
	 */
	private List<String> parsedList = Lists.newArrayList();
	/**
	 * key-链表索引
	 * value-目标类型属性值
	 */
	private Map<Integer,String> indexMap = Maps.newHashMap();
	
	private String indexMapStr;//indexMap字符串形式  1:serialNo,2:salary,3:department

	public <T extends Serializable> T parse(String line, Class<T> targetClass) {
		parseLine(line,parsedList);
		
		if(indexMap.size() == 0) {
			initIndexMap(indexMap);
			if(indexMap.size() == 0) throw new IllegalStateException("indexMap can not be empty.");
		}
		
		Map<String,String> map = Maps.newHashMap();
		for(Integer i : indexMap.keySet()){
			String column = indexMap.get(i);
			if(StringUtils.isEmpty(column)) continue;
			if(parsedList.size() <= i) continue;
			map.put(column, parsedList.get(i));
		}
		T t = null;
		try {
			t = targetClass.newInstance();
			BeanUtils.populate(t, map);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		return t;
	}

	/**
	 * 子类可根据需要填充indexMap
	 * @param indexMap
	 */
	protected void initIndexMap(Map<Integer, String> indexMap) {
		if(StringUtils.isEmpty(indexMapStr)) throw new IllegalStateException("indexMapStr can not be empty.");
		String[] oneArr = StringUtils.split(indexMapStr,",");
		for(String str : oneArr){
			String[] twoArr = StringUtils.split(str,":");
			if(twoArr.length != 2) continue;
			indexMap.put(Integer.parseInt(twoArr[0]), twoArr[1]);
		}
	}

	/**
	 * 解析一行到链表
	 * @param line
	 * @param parsedList
	 */
	protected abstract void parseLine(String line, List<String> parsedList);

	public Map<Integer, String> getIndexMap() {
		return indexMap;
	}

	public void setIndexMap(Map<Integer, String> indexMap) {
		this.indexMap = indexMap;
	}
	
	public String getIndexMapStr() {
		return indexMapStr;
	}

	public void setIndexMapStr(String indexMapStr) {
		this.indexMapStr = indexMapStr;
	}
}
