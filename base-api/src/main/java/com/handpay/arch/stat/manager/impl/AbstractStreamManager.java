package com.handpay.arch.stat.manager.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.collect.Maps;
import com.handpay.arch.stat.bean.StatBean;
import com.handpay.arch.stat.filter.StreamFilter;
import com.handpay.arch.stat.manager.StreamManager;
import com.handpay.arch.stat.parser.StreamParser;

/**
 * 抽象实时计算管理器，定义待填充模型
 * @author sxjiang
 *
 */
public abstract class AbstractStreamManager implements StreamManager,InitializingBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String,ParserDesc> statParserMap = Maps.newHashMap();
	private Map<String,FilterDesc> statFilterMap = Maps.newHashMap();
	private Map<String,StatBean> statBeanMap = Maps.newHashMap();
	
	public void afterPropertiesSet() throws Exception {
		initStatMaps(statParserMap,statFilterMap,statBeanMap);
	}

	protected abstract void initStatMaps(Map<String, ParserDesc> statParserMap, Map<String, FilterDesc> statFilterMap, Map<String, StatBean> statBeanMap);

	@Override
	public Collection<StatBean> stats() {
		return statBeanMap.values();
	}

	public StatBean findStat(String statName) {
		return statBeanMap.get(statName);
	}

	@Override
	public StreamFilter findFilter(String statName) {
		FilterDesc desc = statFilterMap.get(statName);
		
		if(StringUtils.isEmpty(desc.filterClass)) return null;
		StreamFilter filter = null;
		try {
			Class<?> clazz = Class.forName(desc.filterClass);
			filter = (StreamFilter)clazz.newInstance();
			BeanUtils.populate(filter, desc.filterProperties);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		return filter;
	}

	public StreamParser findParser(String statName) {
		ParserDesc desc = statParserMap.get(statName);
		
		StreamParser parser = null;
		try {
			Class<?> clazz = Class.forName(desc.parserClass);
			parser = (StreamParser)clazz.newInstance();
			BeanUtils.populate(parser, desc.parserProperties);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		return parser;
	}

	static class ParserDesc implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String parserClass;
		Map<String,Object> parserProperties;
	}
	
	static class FilterDesc implements Serializable{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		String filterClass;
		Map<String,Object> filterProperties;
	}
}
