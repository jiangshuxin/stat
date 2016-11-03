package com.handpay.arch.stat.controller;

import com.handpay.arch.stat.domain.po.ConfigEntity;
import com.handpay.arch.stat.domain.po.RPCConfig;
import com.handpay.arch.stat.service.ConfigCenterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    public List<ConfigEntity> findAll() {
        return configCenterService.findAll();
    }


    @PostMapping("/config/s")
    public ConfigEntity save(ConfigEntity configInfo){
        return configCenterService.save(configInfo);
    }

    @PostMapping("/config/d")
    public boolean delete(int id){
        configCenterService.delete(id);
        return true;
    }

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
}
