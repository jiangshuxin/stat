package com.handpay.arch.stat.filter.impl;

import org.apache.commons.lang3.StringUtils;

import com.handpay.arch.stat.filter.StreamFilter;

public class FixedLengthStreamFilter implements StreamFilter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer length;

	@Override
	public boolean isValid(String line) {
		return StringUtils.length(line) == getLength();
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}
}
