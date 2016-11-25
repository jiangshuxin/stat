package com.handpay.arch.stat.bean.alarm;

/**
 * Created by fczheng on 2016/11/24.
 */
public class GroupSelect extends Select {
	private static final long serialVersionUID = -3689744020370249580L;
	
	private String group;

    public GroupSelect(String value, String text, String group) {
        this.setValue(value);
        this.setText(text);
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
