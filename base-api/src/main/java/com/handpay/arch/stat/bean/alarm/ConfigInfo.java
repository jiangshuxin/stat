package com.handpay.arch.stat.bean.alarm;

import java.io.Serializable;

/**
 * Created by fczheng on 2016/11/9.
 */
public class ConfigInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    protected int type;
    private String maintainMan;
    private String maintainDate;
    private String description;
    private String businessLine;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBusinessLine() {
        return businessLine;
    }

    public void setBusinessLine(String businessLine) {
        this.businessLine = businessLine;
    }

    public String getTypeName() {
        return TYPE.findName(this.getType());
    }

    public String getMaintainMan() {
        return maintainMan;
    }

    public void setMaintainMan(String maintainMan) {
        this.maintainMan = maintainMan;
    }

    public String getMaintainDate() {
        return maintainDate;
    }

    public void setMaintainDate(String maintainDate) {
        this.maintainDate = maintainDate;
    }

    public enum TYPE {
        SERVER(10001, "服务器监控"),
        RPC(10002, "RPC信息"),
        JVM(10003, "JVM"),
        BUSINESS(10004, "业务数据");


        private int id;
        private String showName;
        private TYPE(int id, String showName) {
            this.id = id;
            this.showName = showName;
        }

        public static String findName(int tid) {
            for (TYPE type : TYPE.values()) {
                if (type.id == tid) {
                    return type.showName;
                }
            }
            throw new IllegalArgumentException("此配置类型不存在");
        }
    }
}
