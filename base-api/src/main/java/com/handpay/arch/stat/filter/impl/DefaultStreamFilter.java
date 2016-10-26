package com.handpay.arch.stat.filter.impl;

import com.handpay.arch.stat.filter.StreamFilter;

public class DefaultStreamFilter implements StreamFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean isValid(String line) {
		return true;
	}
}
