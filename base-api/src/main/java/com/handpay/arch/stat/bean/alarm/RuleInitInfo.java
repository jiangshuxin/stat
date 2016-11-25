package com.handpay.arch.stat.bean.alarm;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * Created by fczheng on 2016/11/24.
 */
public class RuleInitInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Select> userList = Lists.newArrayList();
    private List<Select> groupKeyList = Lists.newArrayList();
    private List<Select> valueKeyList = Lists.newArrayList();
    private List<String> kpiNames = Lists.newArrayList();

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

    public List<String> getKpiNames() {
        return kpiNames;
    }

    public void setKpiNames(List<String> kpiNames) {
        this.kpiNames = kpiNames;
    }
}
