package com.handpay.arch.stat.bean.alarm;

import java.io.Serializable;

/**
 * Created by fczheng on 2016/10/31.
 */
public class RPCConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String businessLine;
    private String appInstance;
    private String appName;
    private String serviceName;
    private String methodName;

    public int getId() {
        return id;
    }

    public String getBusinessLine() {
        return businessLine;
    }

    public String getAppInstance() {
        return appInstance;
    }

    public String getAppName() {
        return appName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBusinessLine(String businessLine) {
        this.businessLine = businessLine;
    }

    public void setAppInstance(String appInstance) {
        this.appInstance = appInstance;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
