package com.handpay.arch.stat.config.model;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.util.ReflectionUtils;

import com.clearspring.analytics.util.Lists;
import com.google.common.collect.Maps;
import com.handpay.arch.common.Constants;
import com.handpay.arch.stat.anno.GroupKey;
import com.handpay.arch.stat.anno.ValueKey;
import com.handpay.arch.stat.bean.CommonResult;
import com.handpay.arch.stat.config.model.entity.AlarmRecordEntity;
import com.handpay.arch.stat.config.model.entity.AlarmRuleEntity;

/**
 * Created by fczheng on 2016/11/18.
 */
public class MetricChecker {
	public static final ScriptEngine jsEngine = new ScriptEngineManager().getEngineByName("JavaScript");
	public static final String ALARM_TEMPLATE = "您好, 这是一条来自星星的预警信息! \n预警项: %1$s, 实际值: %2$s \n预警规则id: %3$d, 规则信息: %4$s";

	private Class<?> clazz;
	private CommonResult result;

	public MetricChecker(Class<?> clazz, CommonResult result) {
		this.clazz = clazz;
		this.result = result;
	}

	private CheckData checkData;
	List<AlarmRecordEntity> records = Lists.newArrayList();

	public Map<String, String> getValueMap() {
		return checkData.getValueMap();
	}

	public List<AlarmRecordEntity> getRecords() {
		return records;
	}

	public void refine() {
		PropertyDescriptor[] pds = ReflectUtils.getBeanProperties(clazz);
		Map<Integer, Object> groupMap = Maps.newHashMap();
		Map<String, String> valueMap = Maps.newHashMap();
		for (PropertyDescriptor pd : pds) {
			Field field = ReflectionUtils.findField(clazz, pd.getName());
			GroupKey annotationGroup = field.getAnnotation(GroupKey.class);
			ValueKey annotationValue = field.getAnnotation(ValueKey.class);
			try {
				Object pdValue = pd.getReadMethod().invoke(result, new Object[] {});
				if (annotationGroup != null)
					groupMap.put(annotationGroup.order(), pdValue);
				else if (annotationValue != null)
					valueMap.put(pd.getName(), String.valueOf(pdValue));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		StringBuffer sb = new StringBuffer();
		Integer[] groupOrderArr = groupMap.keySet().toArray(new Integer[] {});
		Arrays.sort(groupOrderArr);
		for (int order : groupOrderArr)
			sb.append(Constants.SEPARATOR_VERTICAL).append(groupMap.get(order));

		checkData = new CheckData(sb.substring(1), valueMap);
	}

	public void check(List<AlarmRuleEntity> ruleList) {
		for (AlarmRuleEntity rule : ruleList) {
			// 1.去噪
			if (!requireCheckGroup(rule.getGroupKey(), checkData.getGroupKey()))
				continue;
			String realValue = checkData.getValueMap().get(rule.getValueKey());
			if (StringUtils.isEmpty(realValue))
				continue;

			// 2.比较预警值与实际值
			checkRule(realValue, rule);
		}
	}

	private boolean requireCheckGroup(String ruleKey, String checkKey) {
		if (StringUtils.isEmpty(ruleKey))
			return true;
		if (ruleKey.indexOf(checkKey) > -1)
			return true;
		return false;
	}

	private void checkRule(String realValue, AlarmRuleEntity rule) {
		excuteAlarm(rule, realValue);
		if (StringUtils.isNotEmpty(rule.getRuleAnother())) {
			AlarmRuleEntity anotherRule = new AlarmRuleEntity();
			BeanUtils.copyProperties(rule, anotherRule);
			anotherRule.setRule(rule.getRuleAnother());
			anotherRule.setThreshold(rule.getThresholdAnother());
			excuteAlarm(anotherRule, realValue);
		}
	}

	private void excuteAlarm(AlarmRuleEntity rule, String realValue) {
		try {
			boolean isWarning = (boolean) jsEngine.eval(realValue + rule.getRule() + rule.getThreshold());
			if (isWarning)
				records.add(generateAlarmInfo(rule, realValue));
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	private AlarmRecordEntity generateAlarmInfo(AlarmRuleEntity rule, String realValue) {
		AlarmRecordEntity entity = new AlarmRecordEntity();
		entity.setRuleId(rule.getId());
		entity.setAlarmTime(new Timestamp(System.currentTimeMillis()));
		String content = String.format(ALARM_TEMPLATE, rule.getValueKey(), realValue, rule.getId(), rule.getRule() + rule.getThreshold());
		entity.setContent(content);
		return entity;
	}

	public static void main(String[] args) {
		try {
			boolean ret = (boolean) jsEngine.eval("3>5");
			System.out.println(ret);
			System.out.println(String.format(ALARM_TEMPLATE, "responseTime", "120001", 222, ">=120000"));
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
}
