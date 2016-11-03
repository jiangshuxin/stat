package com.handpay.arch.stat.rpc;

import java.io.Serializable;

/**
 * Created by sxjiang on 2016/11/1.
 */
public class DubboQPSResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private String serverHost;
    private String qps;

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getQps() {
        return qps;
    }

    public void setQps(String qps) {
        this.qps = qps;
    }
}
