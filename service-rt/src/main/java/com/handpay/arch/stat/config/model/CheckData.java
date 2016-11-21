package com.handpay.arch.stat.config.model;

import java.util.Map;

/**
 * Created by fczheng on 2016/11/18.
 */
public class CheckData {
    public CheckData() {
    }

    public CheckData(String groupKey, Map<String, String> valueMap) {
        this.groupKey = groupKey;
        this.valueMap = valueMap;
    }


    private String groupKey;
    private Map<String, String> valueMap;

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public Map<String, String> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<String, String> valueMap) {
        this.valueMap = valueMap;
    }
}
