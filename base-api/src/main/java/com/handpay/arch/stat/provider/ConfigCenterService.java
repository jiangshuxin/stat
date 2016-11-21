package com.handpay.arch.stat.provider;


import com.handpay.arch.stat.bean.alarm.AlarmRuleInfo;
import com.handpay.arch.stat.bean.alarm.ConfigInfo;
import com.handpay.arch.stat.bean.alarm.MetricKpi;
import com.handpay.arch.stat.bean.alarm.RPCConfig;

import java.util.List;

/**
 * Created by fczheng on 2016/10/31.
 */
public interface ConfigCenterService {
    /*****************配置中心*******************/
    List<ConfigInfo> findAll();

    ConfigInfo save(ConfigInfo configInfo);

    void delete(int id);


    /*****************特定配置*******************/
    Object saveSpecific(Object config);

    List<?> findSpecificAll();

    void deleteSpecific(int id);

    List<?> findRPC(RPCConfig rpcConfig);

    /***************** 指标/告警 ****************/
    List<MetricKpi> findKpi();

    AlarmRuleInfo findOneKpi(String shortName, int configId);

    boolean saveAlarmRule(AlarmRuleInfo ruleInfo);
}
