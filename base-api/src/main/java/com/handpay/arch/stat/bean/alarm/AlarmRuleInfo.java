package com.handpay.arch.stat.bean.alarm;

import com.google.common.collect.Lists;

import com.handpay.arch.common.Constants;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fczheng on 2016/11/15.
 */
public class AlarmRuleInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String threshold;
    private String rule;
    private String thresholdAnother;
    private String ruleAnother;
    private String noticeWay;
    private int retryTime;
    private String valueKey;
    private String groupKey;
    private String kpiShortName;

    private List<Select> userList = Lists.newArrayList();
    private MetricKpi metricKpi;
    private int configId;

    private List<Select> groupKeyList = Lists.newArrayList();
    private List<Select> valueKeyList = Lists.newArrayList();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getThresholdAnother() {
        return thresholdAnother;
    }

    public void setThresholdAnother(String thresholdAnother) {
        this.thresholdAnother = thresholdAnother;
    }

    public String getRuleAnother() {
        return ruleAnother;
    }

    public void setRuleAnother(String ruleAnother) {
        this.ruleAnother = ruleAnother;
    }

    public String getNoticeWay() {
        return noticeWay;
    }

    public void setNoticeWay(String noticeWay) {
        this.noticeWay = noticeWay;
    }

    public int getRetryTime() {
        return retryTime;
    }

    public void setRetryTime(int retryTime) {
        this.retryTime = retryTime;
    }

    public MetricKpi getMetricKpi() {
        return metricKpi;
    }

    public void setMetricKpi(MetricKpi metricKpi) {
        this.metricKpi = metricKpi;
    }

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public List<Select> getUserList() {
        return userList;
    }

    public void setUserList(List<Select> userList) {
        this.userList = userList;
    }

    public List<Select> getGroupKeyList() {
        return groupKeyList;
    }

    public void setGroupKeyList(List<Select> groupKeyList) {
        this.groupKeyList = groupKeyList;
    }

    public List<Select> getValueKeyList() {
        return valueKeyList;
    }

    public void setValueKeyList(List<Select> valueKeyList) {
        this.valueKeyList = valueKeyList;
    }

    public String getValueKey() {
        return valueKey;
    }

    public void setValueKey(String valueKey) {
        this.valueKey = valueKey;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getKpiShortName() {
        return kpiShortName;
    }

    public void setKpiShortName(String kpiShortName) {
        this.kpiShortName = kpiShortName;
    }


    public String getRuleWithThreshold() {
        if(StringUtils.isEmpty(rule))
            return "";
        return rule.concat(threshold);
    }

    public String getAnotherRuleWithThreshold() {
        if(StringUtils.isEmpty(ruleAnother))
            return "";
        return ruleAnother.concat(thresholdAnother);
    }

    public String getReceiver() {
        String receiver = "";
        if(userList.isEmpty())
            return receiver;

        for (Select select: userList)
            receiver = receiver + Constants.SEPARATOR_COMMA + select.getText();
        return receiver.substring(1);
    }
}
