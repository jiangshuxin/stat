package com.handpay.arch.stat.config.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by fczheng on 2016/11/11.
 */
@Entity(name = "alarm_rule")
public class AlarmRuleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue
    private int id;
    @Column(name = "group_key")
    private String groupKey;
    @Column(name = "value_key")
    private String valueKey;
    @Column(name = "notice_way")
    private String noticeWay;
    @Column(name = "retry_time")
    private int retryTime;
    private double threshold;
    private String rule;
    @Column(name = "threshold_another")
    private double thresholdAnother;
    @Column(name = "rule_another")
    private String ruleAnother;
    @Column(name = "kpi_name")
    private String kpiName;
    @Column(name = "monitor_type")
    private int monitorType;
    @Column(name = "maintain_time")
    private Timestamp maintainTime;

    @Column(name = "config_id")
    private int configId;

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

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public double getThresholdAnother() {
        return thresholdAnother;
    }

    public void setThresholdAnother(double thresholdAnother) {
        this.thresholdAnother = thresholdAnother;
    }

    public String getRuleAnother() {
        return ruleAnother;
    }

    public void setRuleAnother(String ruleAnother) {
        this.ruleAnother = ruleAnother;
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

    public Timestamp getMaintainTime() {
        return maintainTime;
    }

    public void setMaintainTime(Timestamp maintainTime) {
        this.maintainTime = maintainTime;
    }

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }
}
