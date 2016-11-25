package com.handpay.arch.stat.bean.alarm;

import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;

import static com.handpay.arch.common.Constants.BLANK_SPACE;
import static com.handpay.arch.common.Constants.SEPARATOR_COMMA;

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
    private String kpiName;
    private int monitorType;
    private String maintainTime;

    private int configId;

    private List<Select> userList = Lists.newArrayList();
    private List<Select> groupKeyList = Lists.newArrayList();
    private List<Select> valueKeyList = Lists.newArrayList();
//    private MetricKpi metricKpi;

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

    public String getKpiName() {
        return kpiName;
    }

    public void setKpiName(String kpiName) {
        this.kpiName = kpiName;
    }

    public int getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(int monitorType) {
        this.monitorType = monitorType;
    }

    public String getMaintainTime() {
        return maintainTime;
    }

    public void setMaintainTime(String maintainTime) {
        this.maintainTime = maintainTime;
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

    public String getRuleWithThreshold() {
        if(StringUtils.isEmpty(rule))
            return "";
        if(MONITOR_TYPE.THRESHOLD.id != this.getMonitorType())
            return rule;
        return rule.concat(threshold).concat(unionAnother());
    }

    private String unionAnother() {
        if(StringUtils.isEmpty(ruleAnother))
            return "";
        return ruleAnother.concat(thresholdAnother);
    }

    public String getReceiver() {
        String receiver = "";
        if(userList.isEmpty())
            return receiver;

        for (Select select: userList)
            receiver = receiver + SEPARATOR_COMMA + select.getText() + BLANK_SPACE;
        return receiver.substring(1);
    }

    public String getMonitorTypeName() {
        return MONITOR_TYPE.findName(this.getMonitorType());
    }

    public enum MONITOR_TYPE {
        THRESHOLD(0, "阈值"),
        REG_EXP(1, "正则"),
        LIKE_MATCH(2, "模糊匹配");


        private int id;
        private String showName;
        private MONITOR_TYPE(int id, String showName) {
            this.id = id;
            this.showName = showName;
        }

        public static String findName(int tid) {
            for (MONITOR_TYPE type : MONITOR_TYPE.values()) {
                if (type.id == tid) {
                    return type.showName;
                }
            }
            throw new IllegalArgumentException("此预警类型不存在");
        }
    }
}
