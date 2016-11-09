package com.handpay.arch.stat.config.service.impl;


import com.handpay.arch.stat.config.repository.ConfigInfoRepository;
import com.handpay.arch.stat.config.repository.KpiRepository;
import com.handpay.arch.stat.config.repository.RPCConfigRepository;
import com.handpay.arch.stat.domain.ConfigEntity;
import com.handpay.arch.stat.domain.MetricKpi;
import com.handpay.arch.stat.domain.RPCConfig;
import com.handpay.arch.stat.provider.ConfigCenterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fczheng on 2016/10/31.
 */
@Service("configCenterService")
public class ConfigCenterServiceImpl implements ConfigCenterService {

    @Autowired
    private ConfigInfoRepository configInfoRepo;
    @Autowired
    private RPCConfigRepository rpcConfigRepo;
    @Autowired
    private KpiRepository kpiRepo;

    @Override
    public List<ConfigEntity> findAll() {
        return configInfoRepo.findAll();
    }

    @Override
    public ConfigEntity save(ConfigEntity configInfo) {
        return configInfoRepo.save(configInfo);
    }

    @Override
    public void delete(int id) {
        configInfoRepo.delete(id);
    }


    /******************************** RPC *****************************************/
    @Override
    public Object saveSpecific(Object config) {
        if (config instanceof RPCConfig)
            return rpcConfigRepo.save((RPCConfig) config);
        throw new IllegalArgumentException("暂时不支持该类型!!");
    }

    @Override
    public List<?> findSpecificAll() {
        return rpcConfigRepo.findAll();
    }

    @Override
    public void deleteSpecific(int id) {
        rpcConfigRepo.delete(id);
    }

    @Override
    public List<?> findRPC(RPCConfig rpcConfig) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id");
        return rpcConfigRepo.findAll(Example.of(rpcConfig, matcher));
    }


    /******************************** kpi/alarm ***********************************/
    @Override
    public List<MetricKpi> findKpi() {
        return kpiRepo.findAll();
    }
}
