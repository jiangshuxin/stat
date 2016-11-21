package com.handpay.arch.stat.config.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

/**
 * Created by fczheng on 2016/11/15.
 */
@Entity(name = "ref_rule_user")
@IdClass(RefRuleUser.class)
public class RefRuleUser implements Serializable {

    private static final long serialVersionUID = 1L;

    public RefRuleUser() {

    }
    public RefRuleUser(int ruleId, String userId) {
        this.ruleId = ruleId;
        this.userId = userId;
    }

    @Id @Column(name = "rule_id")
    private int ruleId;
    @Id @Column(name = "user_id")
    private String userId;

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
