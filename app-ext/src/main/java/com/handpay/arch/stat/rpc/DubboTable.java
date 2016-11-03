package com.handpay.arch.stat.rpc;

import java.io.Serializable;

/**
 * Created by sxjiang on 2016/11/1.
 */
public class DubboTable implements Serializable {
    private static final long serialVersionUID = 1L;
    private String invokePath;
    private String clientHost;
    private String serverHost;
    private String spanId;
    private String className;
    private String methodName;
    private String type;
    private String time;

    public String getInvokePath() {
        return invokePath;
    }

    public void setInvokePath(String invokePath) {
        this.invokePath = invokePath;
    }

    public String getClientHost() {
        return clientHost;
    }

    public void setClientHost(String clientHost) {
        this.clientHost = clientHost;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
