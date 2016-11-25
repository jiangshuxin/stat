package com.handpay.arch.stat.provider;


import java.util.List;

import com.handpay.arch.stat.bean.alarm.AlarmRuleInfo;
import com.handpay.arch.stat.bean.alarm.ConfigInfo;
import com.handpay.arch.stat.bean.alarm.RPCConfig;
import com.handpay.arch.stat.bean.alarm.RuleInitInfo;
import com.handpay.arch.stat.bean.alarm.Select;

/**
 * Created by fczheng on 2016/10/31.
 */
public interface ConfigCenterService {
    /*****************配置中心*******************/
    List<ConfigInfo> findAll();

    ConfigInfo save(ConfigInfo configInfo);

    void delete(int id);

    ConfigInfo findOne(int id);


    /*****************特定配置*******************/
    Object saveSpecific(Object config);

    List<?> findSpecificAll();

    void deleteSpecific(int id);

    List<?> findRPC(RPCConfig rpcConfig);

    /***************** 指标/告警 ****************/
    List<AlarmRuleInfo> findAllAlarmRule();

    List<AlarmRuleInfo> findRuleByConfigId(int configId);

    List<String> findKpiNames();

    RuleInitInfo findKpiByName(String kpiName);

    boolean saveAlarmRule(AlarmRuleInfo ruleInfo);

    void deleteRule(int id);

    AlarmRuleInfo findOneRule(int id);

    /***************** 用户 ****************/
    List<Select> findUserSelect();
}
