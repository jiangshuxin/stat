package com.handpay.arch.stat.rpc;

import com.handpay.arch.stat.anno.GroupKey;
import com.handpay.arch.stat.anno.ValueKey;
import com.handpay.arch.stat.bean.CommonResult;

import java.io.Serializable;

/**
 * Created by sxjiang on 2016/11/1.
 */
public class DubboQPSResult extends CommonResult implements Serializable {
    private static final long serialVersionUID = 1L;
    @GroupKey
    private String serverHost;
    @ValueKey(order=10)
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

    @Override
    public String toString() {
        return "DubboQPSResult{" +
                "serverHost='" + serverHost + '\'' +
                ", qps='" + qps + '\'' +
                '}';
    }
}
