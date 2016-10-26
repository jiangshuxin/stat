package com.handpay.arch.stat.parser.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

public class FixedLengthParser extends SequentialParser {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * <li>含义：定长下标
	 * <li>用法：英文逗号分隔
	 * <li>举例：0,8,14,17,20
	 * <li>注意：可不从0开始，FixedLengthParser会在头部添加
	 */
	private String indexArrayStr;//定长下标  ,分隔   0,8,14,17,20
	private Integer[] indexArray;//以indexArray属性为准，若空则取indexArrayStr
	
	@Override
	protected void parseLine(String line, List<String> parsedList) {
		Integer[] indexArray = extractTransform();
		if(indexArray[0] != 0){
			indexArray = Lists.asList(0, indexArray).toArray(new Integer[0]);
		}
		for(Integer i=0;i<indexArray.length;i++){
			if(i == indexArray.length - 1) continue;
			Integer index1 = indexArray[i];
			Integer index2 = indexArray[i+1];
			parsedList.add(StringUtils.substring(line, index1, index2));
		}
	}

	private Integer[] extractTransform() {
		if(indexArray != null && indexArray.length > 0){
			return this.indexArray;
		}
		String[] strArray = this.indexArrayStr.split(",");
		List<Integer> intList = Lists.newArrayList();
		for(String str : strArray){
			intList.add(Integer.parseInt(str));
		}
		Integer[] indexArray = intList.toArray(new Integer[0]);
		return indexArray;
	}

	public String getIndexArrayStr() {
		return indexArrayStr;
	}

	public void setIndexArrayStr(String indexArrayStr) {
		this.indexArrayStr = indexArrayStr;
	}

	public Integer[] getIndexArray() {
		return indexArray;
	}

	public void setIndexArray(Integer[] indexArray) {
		this.indexArray = indexArray;
	}
}
