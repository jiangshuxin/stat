package com.handpay.arch.stat.manager;

import java.io.Serializable;
import java.util.Collection;

import com.handpay.arch.stat.bean.StatBean;
import com.handpay.arch.stat.filter.StreamFilter;
import com.handpay.arch.stat.parser.StreamParser;

public interface StreamManager extends Serializable{

	Collection<StatBean> stats();
	
	StatBean findStat(String statName);
	
	StreamFilter findFilter(String statName);
	
	StreamParser findParser(String statName);
}
