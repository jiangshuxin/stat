package com.handpay.arch.stat.filter.impl;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.handpay.arch.stat.filter.StreamFilter;

public class RegexStreamFilter implements StreamFilter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String regex;
	private Pattern pattern;

	@Override
	public boolean isValid(String line) {
		if(StringUtils.isEmpty(line) || StringUtils.isEmpty(regex)) return false;
		if(pattern == null) {
			pattern = Pattern.compile(regex);
		}
		return pattern.matcher(line).matches();
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}
}
