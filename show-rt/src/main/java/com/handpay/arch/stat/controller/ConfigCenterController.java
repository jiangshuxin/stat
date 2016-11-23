package com.handpay.arch.stat.controller;


import com.handpay.arch.stat.bean.alarm.AlarmRuleInfo;
import com.handpay.arch.stat.bean.alarm.ConfigInfo;
import com.handpay.arch.stat.bean.alarm.RPCConfig;
import com.handpay.arch.stat.provider.ConfigCenterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by fczheng on 2016/10/31.
 */
@RestController
public class ConfigCenterController {

    @Autowired
    private ConfigCenterService configCenterService;

    @GetMapping("/config/all")
    public List<?> findAll() {
        List<?> all = configCenterService.findAll();
        return all;
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
    @GetMapping("/kpi/all")
    public List<?> findKpi() {
       return configCenterService.findKpi();
    }

    @GetMapping("/kpi/{configId}/{shortName}")
    public AlarmRuleInfo findOneKpi(@PathVariable(name = "configId") int configId, @PathVariable(name = "shortName") String shortName) {
        return configCenterService.findOneKpi(shortName, configId);
    }

    @PostMapping("kpi/s")
    public boolean saveKpi(@RequestBody AlarmRuleInfo ruleInfo) {
        boolean isOk = configCenterService.saveAlarmRule(ruleInfo);
        return true;
    }

    @GetMapping("alarm/all")
    public List<AlarmRuleInfo> findAllAlarm() {
        List<AlarmRuleInfo> ruleInfos = configCenterService.findAllAlarm();
        return ruleInfos;
    }
}
