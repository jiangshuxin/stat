package com.handpay.arch.stat.config.model.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by fczheng on 2016/11/18.
 */
@Entity(name = "alarm_record")
public class AlarmRecordEntity {
    @Id
    @GeneratedValue
    private int id;
    @Column(name="rule_id")
    private int ruleId;
    private String content;
    @Column(name="alarm_time")
    private Timestamp alarmTime;
    @Column(name="update_time")
    private Timestamp updateTime;
    @Column(name="send_state")
    private int sendState;
    @Column(name="send_number")
    private int sendNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Timestamp alarmTime) {
        this.alarmTime = alarmTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public int getSendState() {
        return sendState;
    }

    public void setSendState(int sendState) {
        this.sendState = sendState;
    }

    public int getSendNumber() {
        return sendNumber;
    }

    public void setSendNumber(int sendNumber) {
        this.sendNumber = sendNumber;
    }
}
