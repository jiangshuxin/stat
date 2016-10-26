package com.handpay.arch.stat.manager.impl;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ClassUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.handpay.arch.stat.anno.GroupKey;
import com.handpay.arch.stat.anno.ValueKey;
import com.handpay.arch.stat.bean.StatBean;

/**
 * 基于Properties文件的实时流管理器,特性如下：
 * <li>分组，以.之前第一个单词作为组名称
 * <li>扫描带注解的StatBean
 * @author sxjiang
 *
 */
public class PropertiesStreamManager extends AbstractStreamManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Properties properties = new Properties();
	private Map<String,Map<String,String>> namePropMap = Maps.newHashMap();
	private List<String> filePaths;
	
	public static final String DOT = ".";
	public static final String TABLE = "table";
	public static final String RESULT = "result";
	public static final String SQL = "sql";
	public static final String TOPIC = "topic";
	public static final String RESULT_TOPIC = "resultTopic";
	public static final String PARSER = "parser";
	public static final String FILTER = "filter";
	public static final String BATCH_DURATION = "batchDuration";
	public static final String WINDOW_DURATION = "windowDuration";
	public static final String SLIDE_DURATION = "slideDuration";
	public static final String PROPERTY_MAP = "propertyMap";

	@SuppressWarnings("unchecked")
	@Override
	protected void initStatMaps(Map<String, ParserDesc> statParserMap,Map<String, FilterDesc> statFilterMap,Map<String, StatBean> statBeanMap) {
		if(filePaths == null || filePaths.size() == 0) throw new IllegalStateException("filePath can not be empty.");
		try {
			for(String path : filePaths){
				Properties p = new Properties();
				ClassPathResource res = new ClassPathResource(path);
				p.load(res.getInputStream());
				properties.putAll(p);
			}
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		
		for(Object keyObj : properties.keySet()){
			String key = keyObj.toString();
			String name = StringUtils.substringBefore(key, ".");
			if(namePropMap.containsKey(name)){
				Map<String,String> innerMap = namePropMap.get(name);
				innerMap.put(key, properties.getProperty(key));
			}else{
				Map<String,String> innerMap = Maps.newHashMap();
				innerMap.put(key, properties.getProperty(key));
				namePropMap.put(name, innerMap);
			}
		}
		
		for(String key : namePropMap.keySet()){
			StatBean bean = new StatBean();
			bean.setName(key);
			Map<String,String> innerMap = namePropMap.get(key);
			String tableClass = innerMap.get(StringUtils.join(key,DOT,TABLE));
			innerMap.remove(StringUtils.join(key,DOT,TABLE));
			String resultClass = innerMap.get(StringUtils.join(key,DOT,RESULT));
			innerMap.remove(StringUtils.join(key,DOT,RESULT));
			String parserClass = innerMap.get(StringUtils.join(key,DOT,PARSER));
			innerMap.remove(StringUtils.join(key,DOT,PARSER));
			String filterClass = innerMap.get(StringUtils.join(key,DOT,FILTER));
			innerMap.remove(StringUtils.join(key,DOT,FILTER));
			String topic = innerMap.get(StringUtils.join(key,DOT,TOPIC));
			innerMap.remove(StringUtils.join(key,DOT,TOPIC));
			String resultTopic = innerMap.get(StringUtils.join(key,DOT,RESULT_TOPIC));
			innerMap.remove(StringUtils.join(key,DOT,RESULT_TOPIC));
			String sql = innerMap.get(StringUtils.join(key,DOT,SQL));
			innerMap.remove(StringUtils.join(key,DOT,SQL));
			String batchDuration = innerMap.get(StringUtils.join(key,DOT,BATCH_DURATION));
			innerMap.remove(StringUtils.join(key,DOT,BATCH_DURATION));
			String windowDuration = innerMap.get(StringUtils.join(key,DOT,WINDOW_DURATION));
			innerMap.remove(StringUtils.join(key,DOT,WINDOW_DURATION));
			String slideDuration = innerMap.get(StringUtils.join(key,DOT,SLIDE_DURATION));
			innerMap.remove(StringUtils.join(key,DOT,SLIDE_DURATION));
			bean.setSql(StringUtils.split(sql, ";"));
			bean.setTopic(topic);
			bean.setResultTopic(resultTopic);
			bean.setBatchDuration(Long.parseLong(batchDuration));
			bean.setWindowDuration(Long.parseLong(windowDuration));
			bean.setSlideDuration(Long.parseLong(slideDuration));
			try {
				bean.setTableClass(Class.forName(tableClass));
				bean.setResultClass(Class.forName(resultClass));
			} catch (ClassNotFoundException e) {
				throw new IllegalStateException(e);
			}
			extractAnnotation(bean);

			//parser class
			ParserDesc pDesc = new ParserDesc();
			pDesc.parserClass = parserClass;
			Map<String,Object> parserPropMap = Maps.newHashMap();
			pDesc.parserProperties = parserPropMap;
			
			//filter class
			FilterDesc fDesc = new FilterDesc();
			fDesc.filterClass = filterClass;
			Map<String,Object> filterPropMap = Maps.newHashMap();
			fDesc.filterProperties = filterPropMap;
			for(String innerKey : innerMap.keySet()){
				String[] innerArr = StringUtils.split(innerKey, ".");
				if(innerArr.length < 3) continue;
				if(StringUtils.equalsIgnoreCase(innerArr[1], FILTER) && StringUtils.isNotEmpty(filterClass)){
					extractPropMap(innerMap, filterClass, filterPropMap, innerKey, innerArr);
				}else if(StringUtils.equalsIgnoreCase(innerArr[1], PARSER)){
					extractPropMap(innerMap, parserClass, parserPropMap, innerKey, innerArr);
				}else if(StringUtils.equalsIgnoreCase(innerArr[1], PROPERTY_MAP)){
					extractPropMap(innerMap, innerKey, innerArr,bean);
				}
			}
			statParserMap.put(key, pDesc);
			statFilterMap.put(key, fDesc);
			statBeanMap.put(key, bean);
		}
	}

	private void extractPropMap(Map<String, String> innerMap, String innerKey, String[] innerArr, StatBean bean) {
		String column = innerArr[2];
		String value = innerMap.get(innerKey);
		bean.getPropertyMap().put(column, value);
	}

	@SuppressWarnings("unchecked")
	private void extractAnnotation(StatBean bean) {
		SortedSet<KeyOrder> set = new TreeSet<KeyOrder>(new KeyOrderComparator());
		for(Field f : bean.getResultClass().getFields()){
			f.setAccessible(true);
			GroupKey gk = AnnotationUtils.getAnnotation(f, GroupKey.class);
			if(gk != null){
				bean.setGroupKey(f.getName());
			}
			ValueKey vk = AnnotationUtils.getAnnotation(f, ValueKey.class);
			if(vk != null){
				set.add(new KeyOrder(f.getName(),vk.order()));
			}
		}
		List<String> vkList = Lists.newArrayList();
		for(KeyOrder ko : set){
			vkList.add(ko.getKey());
		}
		bean.setValueKeyList(vkList);
	}

	private void extractPropMap(Map<String, String> innerMap, String targetClass, Map<String, Object> targetPropMap,
			String innerKey, String[] innerArr) {
		String column = innerArr[2];
		String value = innerMap.get(innerKey);
		Object finalValue = value;
		try {
			Class<?> clazz = Class.forName(targetClass);
			Field field = FieldUtils.getField(clazz, column);
			if(field != null){
				Class<?> fieldType = field.getType();
				if(ClassUtils.hasConstructor(fieldType, String.class)){
					finalValue = ClassUtils.getConstructorIfAvailable(fieldType, String.class).newInstance(value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		targetPropMap.put(column, finalValue);
	}

	public List<String> getFilePaths() {
		return filePaths;
	}

	public void setFilePaths(List<String> filePaths) {
		this.filePaths = filePaths;
	}
	
	public static class KeyOrder implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String key;
		private Integer order;
		public KeyOrder(String key, Integer order) {
			super();
			this.key = key;
			this.order = order;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public Integer getOrder() {
			return order;
		}
		public void setOrder(Integer order) {
			this.order = order;
		}
	}
	
	public static class KeyOrderComparator implements Comparator<KeyOrder>,Serializable{
		private static final long serialVersionUID = 1L;
		@Override
		public int compare(KeyOrder o1, KeyOrder o2) {
			return o1.getOrder().compareTo(o2.getOrder());
		}
	}
}
