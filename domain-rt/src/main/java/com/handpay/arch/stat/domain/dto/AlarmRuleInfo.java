package com.handpay.arch.stat.domain.dto;

import com.handpay.arch.stat.domain.MetricKpi;
import com.handpay.arch.stat.domain.User;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by fczheng on 2016/11/15.
 */
public class AlarmRuleInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String groupKey;  //选中的groupKey
    private String valueKey;  //选中的valueKey
    private String threshold;
    private String rule;
    private String thresholdAnother;
    private String ruleAnother;
    private String noticeWay;
    private int retryTime;

    private List<User> userList;
    private MetricKpi metricKpi;
    private int configId;

    private List<String> groupKeyList;
    private List<String> valueKeyList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getValueKey() {
        return valueKey;
    }

    public void setValueKey(String valueKey) {
        this.valueKey = valueKey;
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

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
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

    public List<String> getGroupKeyList() {
        return groupKeyList;
    }

    public void setGroupKeyList(List<String> groupKeyList) {
        this.groupKeyList = groupKeyList;
    }

    public List<String> getValueKeyList() {
        return valueKeyList;
    }

    public void setValueKeyList(List<String> valueKeyList) {
        this.valueKeyList = valueKeyList;
    }
}
