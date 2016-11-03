package com.handpay.arch.stat.service.impl;

import com.handpay.arch.stat.domain.po.ConfigEntity;
import com.handpay.arch.stat.domain.po.RPCConfig;
import com.handpay.arch.stat.repository.ConfigInfoRepository;
import com.handpay.arch.stat.repository.RPCConfigRepository;
import com.handpay.arch.stat.service.ConfigCenterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by fczheng on 2016/10/31.
 */
@Service
public class ConfigCenterServiceImpl implements ConfigCenterService {

    @Autowired
    private ConfigInfoRepository configInfoRepo;
    @Autowired
    private RPCConfigRepository rpcConfigRepo;

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
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id").withIgnoreNullValues();
        return rpcConfigRepo.findAll(Example.of(rpcConfig, matcher));
    }
}
