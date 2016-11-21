package com.handpay.arch.stat.config.service.impl;


import com.google.common.collect.Lists;

import com.handpay.arch.common.Constants;
import com.handpay.arch.stat.bean.StatBean;
import com.handpay.arch.stat.bean.alarm.AlarmRuleInfo;
import com.handpay.arch.stat.bean.alarm.ConfigInfo;
import com.handpay.arch.stat.bean.alarm.MetricKpi;
import com.handpay.arch.stat.bean.alarm.RPCConfig;
import com.handpay.arch.stat.bean.alarm.User;
import com.handpay.arch.stat.config.model.entity.AlarmRuleEntity;
import com.handpay.arch.stat.config.model.entity.ConfigEntity;
import com.handpay.arch.stat.config.model.entity.MetricKpiEntity;
import com.handpay.arch.stat.config.model.entity.RPCConfigEntity;
import com.handpay.arch.stat.config.model.entity.RefConfigKpi;
import com.handpay.arch.stat.config.model.entity.RefRuleUser;
import com.handpay.arch.stat.config.repository.AlarmRuleRepository;
import com.handpay.arch.stat.config.repository.ConfigEntityRepository;
import com.handpay.arch.stat.config.repository.KpiRepository;
import com.handpay.arch.stat.config.repository.RPCConfigRepository;
import com.handpay.arch.stat.config.repository.RefConfigKpiRepository;
import com.handpay.arch.stat.config.repository.RefRuleUserRepository;
import com.handpay.arch.stat.config.repository.jdbc.JdbcRepository;
import com.handpay.arch.stat.manager.StreamManager;
import com.handpay.arch.stat.provider.ConfigCenterService;
import com.handpay.arch.stat.provider.StreamProvider;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Created by fczheng on 2016/10/31.
 */
@Service("configCenterService")
public class ConfigCenterServiceImpl implements ConfigCenterService {

    @Autowired
    private ConfigEntityRepository configInfoRepo;
    @Autowired
    private RPCConfigRepository rpcConfigRepo;
    @Autowired
    private KpiRepository kpiRepo;
    @Autowired
    private RefConfigKpiRepository refRepo;
    @Autowired
    private AlarmRuleRepository alarmRuleRepository;
    @Autowired
    private RefRuleUserRepository refRuleUserRepository;

    @Autowired
    private JdbcRepository jdbcRepository;

    @Autowired
    private StreamManager streamManager;
    @Autowired
    private StreamProvider streamProvider;

    @Override
    public List<ConfigInfo> findAll() {
        List<ConfigEntity> entityList = jdbcRepository.findAllConfig();
        List<ConfigInfo> infoList = Lists.newArrayList();
        for (ConfigEntity entity: entityList) {
            ConfigInfo info = new ConfigInfo();
            BeanUtils.copyProperties(entity, info);
            infoList.add(info);
        }
        return infoList;
    }

    @Override
    public ConfigInfo save(ConfigInfo configInfo) {
        ConfigEntity entity = new ConfigEntity();
        BeanUtils.copyProperties(configInfo, entity);
        entity = configInfoRepo.save(entity);

        saveRef(configInfo.getKpiNames().split(Constants.SEPARATOR_COMMA), entity.getId());

        configInfo.setId(entity.getId());
        return configInfo;
    }

    private void saveRef(String[] kpiArr, int configId) {
        if (kpiArr == null || kpiArr.length == 0)
            return;
        List<RefConfigKpi> refList = Lists.newArrayList();
        for (String shortName : kpiArr)
            refList.add(new RefConfigKpi(configId, shortName));
        refRepo.save(refList);
    }

    @Override
    @Transactional
    public void delete(int id) {
        refRepo.deleteByConfigId(id);
        configInfoRepo.delete(id);
    }


    /******************************** RPC *****************************************/
    @Override
    public Object saveSpecific(Object config) {
        if (config instanceof RPCConfig) {
            RPCConfigEntity entity = new RPCConfigEntity();
            BeanUtils.copyProperties((RPCConfig) config, entity);
            return rpcConfigRepo.save(entity);
        }

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
        RPCConfigEntity entity =  new RPCConfigEntity();
        BeanUtils.copyProperties(rpcConfig, entity);

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnorePaths("id");
        return rpcConfigRepo.findAll(Example.of(entity, matcher));
    }


    /******************************** kpi/alarm ***********************************/
    @Override
    public List<MetricKpi> findKpi() {
        Collection<StatBean> stats = streamManager.stats();
        List<MetricKpi> kpis = Lists.newArrayList();
        for (StatBean stat: stats) {
            MetricKpi kpi = new MetricKpi();
            kpi.setShortName(stat.getName());
            kpis.add(kpi);
        }
        return kpis;
    }

    @Override
    public AlarmRuleInfo findOneKpi(String shortName, int configId) {
        MetricKpi kpi = new MetricKpi();
        MetricKpiEntity kpiEntity = kpiRepo.findOne(shortName);
        if (kpiEntity == null)
            kpiEntity = new MetricKpiEntity(shortName);
        BeanUtils.copyProperties(kpiEntity, kpi);

        AlarmRuleEntity entity = alarmRuleRepository.findByKpiShortNameAndConfigId(shortName, configId);

        AlarmRuleInfo info = new AlarmRuleInfo();
        StatBean stat = streamManager.findStat(shortName);
        info.setGroupKeyList(streamProvider.findGroupByName(shortName));
        info.setValueKeyList(stat.getValueKeyList());
        info.setMetricKpi(kpi);
        if (entity != null) {
            BeanUtils.copyProperties(entity, info);
            if (entity.getThreshold() > Constants.KPI_PRECISION)
                info.setThreshold(String.valueOf(entity.getThreshold()));
            if (entity.getThresholdAnother() > Constants.KPI_PRECISION)
                info.setThresholdAnother(String.valueOf(entity.getThresholdAnother()));
        } else {
            info.setConfigId(configId);
        }
        return info;
    }

    @Override
    @Transactional
    public boolean saveAlarmRule(AlarmRuleInfo ruleInfo) {
        try {
            AlarmRuleEntity ruleEntity = new AlarmRuleEntity();
            BeanUtils.copyProperties(ruleInfo, ruleEntity);
            if(StringUtils.isNotEmpty(ruleInfo.getThreshold()))
                ruleEntity.setThreshold(Float.parseFloat(ruleInfo.getThreshold()));
            if(StringUtils.isNotEmpty(ruleInfo.getThresholdAnother()))
                ruleEntity.setThresholdAnother(Float.parseFloat(ruleInfo.getThresholdAnother()));
            ruleEntity.setKpiShortName(ruleInfo.getMetricKpi().getShortName());
            alarmRuleRepository.save(ruleEntity);  //保存规则

            MetricKpiEntity kpiEntity = new MetricKpiEntity();
            BeanUtils.copyProperties(ruleInfo.getMetricKpi(), kpiEntity);
            kpiRepo.save(kpiEntity);  //保存指标

            List<RefRuleUser> refList = Lists.newArrayList();
            for (User user: ruleInfo.getUserList()) {
                refList.add(new RefRuleUser(ruleEntity.getId(), user.getUserId()));
            }
            refRuleUserRepository.save(refList);  //保存规则和用户关联
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
