package com.handpay.arch.stat.domain.dto;

import com.handpay.arch.stat.domain.ConfigEntity;

/**
 * Created by fczheng on 2016/11/9.
 */
public class ConfigInfo extends ConfigEntity {

    private String typeName;

    public String getTypeName() {
        return TYPE.findName(this.getType());
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
