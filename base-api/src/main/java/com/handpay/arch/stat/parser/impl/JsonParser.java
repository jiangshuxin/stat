package com.handpay.arch.stat.parser.impl;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.handpay.arch.stat.parser.StreamParser;

public class JsonParser implements StreamParser {

	/**
	 * 
	 */
	private static final long serialVersionUID = -834856992256106800L;

	public <T extends Serializable> T parse(String line, Class<T> targetClass) {
		return JSON.parseObject(line, targetClass);
	}
}
