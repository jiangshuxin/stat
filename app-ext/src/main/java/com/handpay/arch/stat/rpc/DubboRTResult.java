package com.handpay.arch.stat.rpc;

import com.handpay.arch.stat.anno.GroupKey;
import com.handpay.arch.stat.anno.ValueKey;
import com.handpay.arch.stat.bean.CommonResult;

import java.io.Serializable;

/**
 * Created by sxjiang on 2016/11/1.
 * 某台机器上某服务某方法的响应时间
 */
public class DubboRTResult extends CommonResult implements Serializable {
    private static final long serialVersionUID = 1L;
    @GroupKey(order=10)
    private String serverHost;
    private String invokePath;
    private String spanId;
    @GroupKey(order=20)
    private String className;
    @GroupKey(order=30)
    private String methodName;
    @ValueKey(order=10)
    private String responseTime;

    public String getInvokePath() {
        return invokePath;
    }

    public void setInvokePath(String invokePath) {
        this.invokePath = invokePath;
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

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    @Override
    public String toString() {
        return "DubboRTResult{" +
                "invokePath='" + invokePath + '\'' +
                ", serverHost='" + serverHost + '\'' +
                ", spanId='" + spanId + '\'' +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", responseTime='" + responseTime + '\'' +
                '}' +super.toString();
    }
}
