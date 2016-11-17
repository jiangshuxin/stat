package com.handpay.arch.stat.provider;


import com.handpay.arch.stat.domain.ConfigEntity;
import com.handpay.arch.stat.domain.MetricKpi;
import com.handpay.arch.stat.domain.RPCConfig;
import com.handpay.arch.stat.domain.dto.AlarmRuleInfo;
import com.handpay.arch.stat.domain.dto.ConfigInfo;

import java.util.List;

/**
 * Created by fczheng on 2016/10/31.
 */
public interface ConfigCenterService {
    /*****************配置中心*******************/
    List<ConfigInfo> findAll();

    ConfigEntity save(ConfigInfo configInfo);

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
