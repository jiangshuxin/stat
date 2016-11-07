package com.handpay.arch.stat.rpc;

import com.handpay.arch.stat.bean.CommonResult;

import java.io.Serializable;

/**
 * Created by sxjiang on 2016/11/1.
 */
public class DubboStatusResult extends CommonResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private String serverHost;
    private String successRatio;

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getSuccessRatio() {
        return successRatio;
    }

    public void setSuccessRatio(String successRatio) {
        this.successRatio = successRatio;
    }

    @Override
    public String toString() {
        return "DubboStatusResult{" +
                "serverHost='" + serverHost + '\'' +
                ", successRatio='" + successRatio + '\'' +
                '}';
    }
}
