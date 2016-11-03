package com.handpay.arch.stat.domain.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by fczheng on 2016/10/31.
 */
@Entity(name="rpc_config")
public class RPCConfig {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "business_line")
    private String businessLine;
    @Column(name = "app_instance")
    private String appInstance;
    @Column(name = "app_name")
    private String appName;
    @Column(name = "service_name")
    private String serviceName;
    @Column(name = "method_name")
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
