package com.handpay.arch.stat.filter;

import java.io.Serializable;

public interface StreamFilter extends Serializable{

	boolean isValid(String line);
}
