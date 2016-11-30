package com.handpay.arch.stat.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.handpay.arch.stat.bean.alarm.AlarmRuleInfo;
import com.handpay.arch.stat.bean.alarm.ConfigInfo;
import com.handpay.arch.stat.bean.alarm.RPCConfig;
import com.handpay.arch.stat.bean.alarm.RuleInitInfo;
import com.handpay.arch.stat.provider.ConfigCenterService;

/**
 * Created by fczheng on 2016/10/31.
 */
@RestController
public class ConfigCenterController {

    @Autowired
    private ConfigCenterService configCenterService;

    @GetMapping("/config/all/{page}")
    public Page<?> findAll(@PathVariable(name = "page") int page) {
        Page<?> all = configCenterService.findAll(page);
        return all;
    }

    @GetMapping("/config/g/{id}")
    public ConfigInfo findOne(@PathVariable(name = "id") int id) {
        ConfigInfo info = configCenterService.findOne(id);
        return info;
    }


    @PostMapping("/config/s")
    public ConfigInfo save(ConfigInfo configInfo){
        ConfigInfo ce = configCenterService.save(configInfo);
        return ce;
    }

    @PostMapping("/config/d")
    public boolean delete(int id){
        configCenterService.delete(id);
        return true;
    }


    /******************************** RPC *****************************************/
    @GetMapping("/config/rpc/all")
    public List<?> findRPCAll() {
        return configCenterService.findSpecificAll();
    }

    @PostMapping("/config/rpc/s")
    public Object save(RPCConfig rpcConfig){
        return configCenterService.saveSpecific(rpcConfig);
    }

    @PostMapping("/config/rpc/d")
    public boolean deleteSpecific(int id){
        configCenterService.deleteSpecific(id);
        return true;
    }

    @GetMapping("/config/rpc/condition")
    public List<?> findRPC(RPCConfig rpcConfig) {
        return configCenterService.findRPC(rpcConfig);
    }


    /******************************** 指标/告警 ************************************/
    @GetMapping("/rule/all")
    public List<AlarmRuleInfo> findAllAlarmRule() {
        List<AlarmRuleInfo> ruleInfos = configCenterService.findAllAlarmRule();
        return ruleInfos;
    }

    @GetMapping("config/{configId}/rule")
    public List<AlarmRuleInfo> findRuleByConfig(@PathVariable(name = "configId") int configId) {
        List<AlarmRuleInfo> ruleInfos = configCenterService.findRuleByConfigId(configId);
        return ruleInfos;
    }

    @GetMapping("/rule/init")
    public RuleInitInfo initRule() {
        RuleInitInfo init = new RuleInitInfo();
        init.setKpiNames(configCenterService.findKpiNames());
        init.setUserList(configCenterService.findUserSelect());
        return init;
    }

    @GetMapping("/kpi/g/{kpiName}")
    public RuleInitInfo findKpiByName(@PathVariable(name = "kpiName") String kpiName) {
        RuleInitInfo init = configCenterService.findKpiByName(kpiName);
        return init;
    }

    @GetMapping("/rule/g/{id}")
    public AlarmRuleInfo findOneKpi(@PathVariable(name = "id") int id) {
        return configCenterService.findOneRule(id);
    }

    @PostMapping("rule/s")
    public boolean saveKpi(@RequestBody AlarmRuleInfo ruleInfo) {
        boolean isOk = configCenterService.saveAlarmRule(ruleInfo);
        return isOk;
    }

    @PostMapping("rule/d")
    public boolean deleteRule(int id){
        configCenterService.deleteRule(id);
        return true;
    }
}
