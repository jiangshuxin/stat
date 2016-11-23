package com.handpay.arch.stat.bean.alarm;

import java.io.Serializable;

/**
 * Created by fczheng on 2016/11/23.
 */
public class Select implements Serializable {

    private static final long serialVersionUID = 1L;

    private String value;
    private String text;
    private boolean isSelected;
    private String group;  //分组下拉框

    public Select() {

    }

    /**
     * 此构造方法下拉框(value == text)
     */
    public Select(String value) {
        this.value = value;
        this.text = value;
    }

    public Select(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public Select(String value, String text, String group) {
        this.value = value;
        this.text = text;
        this.group = group;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
