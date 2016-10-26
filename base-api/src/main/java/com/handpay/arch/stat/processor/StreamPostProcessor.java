package com.handpay.arch.stat.processor;

import java.io.Serializable;
import java.util.List;

import com.handpay.arch.stat.bean.StatBean;

public interface StreamPostProcessor {

	void process(StatBean statBean,List<Serializable> list);
}
