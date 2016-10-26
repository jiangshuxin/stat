package com.handpay.arch.stat.parser.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class SeparatorParser extends SequentialParser {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6019211892428461108L;
	private String separator;

	@Override
	protected void parseLine(String line, List<String> parsedList) {
		String[] array = StringUtils.split(line, separator);
		for(String str : array){
			parsedList.add(str);
		}
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}
}
