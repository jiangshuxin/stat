package com.handpay.arch.stat.parser;

import java.io.Serializable;

/**
 * @author sxjiang
 *
 */
public interface StreamParser extends Serializable{

	public <T extends Serializable> T parse(String line,Class<T> targetClass);
}
