package com.handpay.arch.stat.config.model;

import com.google.common.collect.Maps;

import com.handpay.arch.common.Constants;
import com.handpay.arch.stat.anno.GroupKey;
import com.handpay.arch.stat.anno.ValueKey;
import com.handpay.arch.stat.bean.CommonResult;
import com.handpay.arch.stat.config.model.entity.AlarmEntity;
import com.handpay.arch.stat.config.model.entity.AlarmRuleEntity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.core.ReflectUtils;

import java.beans.PropertyDescriptor;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Created by fczheng on 2016/11/18.
 */
public class MetricChecker {
    public static final ScriptEngine jsEngine = new ScriptEngineManager().getEngineByName("JavaScript");
    public static final String ALARM_TEMPLATE = "您好, 这是一条来自星星的预警信息! \n预警项: s%, 实际值: s% \n预警规则id: d%, 规则信息: s%";

    public static CheckData refine(Class<?> clazz, CommonResult result) {
        PropertyDescriptor[] pds = ReflectUtils.getBeanProperties(clazz);
        Map<Integer, Object> groupMap = Maps.newHashMap();
        Map<String, String> valueMap = Maps.newHashMap();
        for (PropertyDescriptor pd : pds) {
            GroupKey annotationGroup = pd.getReadMethod().getAnnotation(GroupKey.class);
            ValueKey annotationValue = pd.getReadMethod().getAnnotation(ValueKey.class);
            try {
                Object pdValue = pd.getReadMethod().invoke(result, null);
                if (annotationGroup != null) {
                    groupMap.put(annotationGroup.order(), pdValue);
                } else if (annotationValue != null) {
                    valueMap.put(pd.getName(), String.valueOf(pdValue));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        StringBuffer sb = new StringBuffer();
        Integer[] groupOrderArr = groupMap.keySet().toArray(new Integer[]{});
        Arrays.sort(groupOrderArr);
        for (int order : groupOrderArr)
            sb.append(Constants.SEPARATOR_VERTICAL).append(groupMap.get(order));

        return new CheckData(sb.substring(1), valueMap);
    }

    public static List<AlarmEntity> check(CheckData checkData, List<AlarmRuleEntity> ruleList) {
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
        return null;
    }

    private static boolean requireCheckGroup(String ruleKey, String checkKey) {
        if (StringUtils.isEmpty(ruleKey))
            return true;
        if (ruleKey.indexOf(checkKey) > -1)
            return true;
        return false;
    }

    private static void checkRule(String realValue, AlarmRuleEntity rule) {
        try {
            boolean isWarning = (boolean) jsEngine.eval(realValue + rule.getRule() + rule.getThreshold());
            if(isWarning) {
                generateAlarmInfo(true, rule, realValue);
            }
            boolean isWarningAnother = false;
            if (StringUtils.isNotEmpty(rule.getRuleAnother()))
                isWarningAnother = (boolean) jsEngine.eval(realValue + rule.getRuleAnother() + rule.getThresholdAnother());
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    private static AlarmEntity generateAlarmInfo(boolean isFirst, AlarmRuleEntity rule, String realValue) {
        AlarmEntity entity = new AlarmEntity();
        entity.setRuleId(rule.getId());
        entity.setAlarmTime(new Date(System.currentTimeMillis()));
        String content = ALARM_TEMPLATE;
        if (isFirst) {
            String.format(content, rule.getValueKey(), realValue, rule.getId(), rule.getRule() + rule.getThreshold());
        } else {
            String.format(content, rule.getValueKey(), realValue, rule.getId(), rule.getRuleAnother() + rule.getThresholdAnother());
        }
        entity.setContent(content);
        return entity;
    }

    public static void main(String[] args) {
        try {
            boolean ret = (boolean) jsEngine.eval("3>5");
            System.out.println(ret);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
