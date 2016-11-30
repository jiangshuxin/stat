package com.handpay.arch.stat.config.service.impl;


import com.google.common.collect.Lists;

import com.handpay.arch.stat.bean.StatBean;
import com.handpay.arch.stat.bean.alarm.AlarmRuleInfo;
import com.handpay.arch.stat.bean.alarm.ConfigInfo;
import com.handpay.arch.stat.bean.alarm.GroupSelect;
import com.handpay.arch.stat.bean.alarm.RPCConfig;
import com.handpay.arch.stat.bean.alarm.RuleInitInfo;
import com.handpay.arch.stat.bean.alarm.Select;
import com.handpay.arch.stat.bean.page.PageableImpl;
import com.handpay.arch.stat.bean.page.PageableRequest;
import com.handpay.arch.stat.config.model.entity.AlarmRuleEntity;
import com.handpay.arch.stat.config.model.entity.ConfigEntity;
import com.handpay.arch.stat.config.model.entity.RPCConfigEntity;
import com.handpay.arch.stat.config.model.entity.RefRuleUser;
import com.handpay.arch.stat.config.model.entity.UserEntity;
import com.handpay.arch.stat.config.repository.AlarmRuleRepository;
import com.handpay.arch.stat.config.repository.ConfigEntityRepository;
import com.handpay.arch.stat.config.repository.RPCConfigRepository;
import com.handpay.arch.stat.config.repository.RefRuleUserRepository;
import com.handpay.arch.stat.config.repository.UserRepository;
import com.handpay.arch.stat.manager.StreamManager;
import com.handpay.arch.stat.provider.ConfigCenterService;
import com.handpay.arch.stat.provider.StreamProvider;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import static com.handpay.arch.common.Constants.FAST_SDF;
import static com.handpay.arch.common.Constants.KPI_PRECISION;
import static com.handpay.arch.common.Constants.SEPARATOR_COMMA;

/**
 * Created by fczheng on 2016/10/31.
 */
@Service("configCenterService")
public class ConfigCenterServiceImpl implements ConfigCenterService {

    private static final Converter<ConfigEntity, ConfigInfo> configConverter = new Converter<ConfigEntity, ConfigInfo>(){
        @Override
        public ConfigInfo convert(ConfigEntity source) {
            ConfigInfo info = new ConfigInfo();
            BeanUtils.copyProperties(source, info);
            info.setMaintainDate(String.valueOf(source.getMaintainDate()));
            return info;
        }
    };

    @Autowired
    private ConfigEntityRepository configInfoRepo;
    @Autowired
    private RPCConfigRepository rpcConfigRepo;
    @Autowired
    private AlarmRuleRepository alarmRuleRepository;
    @Autowired
    private RefRuleUserRepository refRuleUserRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StreamManager streamManager;
    @Autowired
    private StreamProvider streamProvider;

    @Override
    public Page<ConfigInfo> findAll(int page) {
        PageableRequest pageable = new PageableRequest(page);  //页面

        //1.数据层
        PageRequest pageRequest = new PageRequest(page, pageable.getPageSize());
        Page<ConfigEntity> entityPage = configInfoRepo.findAll(pageRequest);
        Page<ConfigInfo> infoPage = entityPage.map(configConverter);

        //2.转页面数据(dubbo使用原生pagable异常)
        Page<ConfigInfo> result = new PageableImpl<ConfigInfo>(infoPage.getContent(), pageable, infoPage.getTotalElements());
        return result;
    }

    @Override
    public ConfigInfo save(ConfigInfo configInfo) {
        ConfigEntity entity = new ConfigEntity();
        BeanUtils.copyProperties(configInfo, entity);
        entity.setMaintainDate(new Date(System.currentTimeMillis()));
        entity = configInfoRepo.save(entity);
        configInfo.setId(entity.getId());
        return configInfo;
    }

    @Override
    @Transactional
    public void delete(int id) {
        configInfoRepo.delete(id);
    }

    @Override
    public ConfigInfo findOne(int id) {
        ConfigEntity entity = configInfoRepo.findOne(id);
        ConfigInfo info = new ConfigInfo();
        BeanUtils.copyProperties(entity, info);
        return info;
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
    @SuppressWarnings("rawtypes")
	@Override
    public List<String> findKpiNames() {
        Collection<StatBean> stats = streamManager.stats();
        List<String> kpis = Lists.newArrayList();
        for (StatBean stat : stats)
            kpis.add(stat.getName());
        return kpis;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public RuleInitInfo findKpiByName(String kpiName) {
        RuleInitInfo info = new RuleInitInfo();
        StatBean stat = streamManager.findStat(kpiName);
        for (String value : streamProvider.findGroupByName(kpiName)) {
            Select s = new Select(value);
            info.getGroupKeyList().add(s);
        }
        for (Object v : stat.getValueKeyList()) {
            Select s = new Select(String.valueOf(v));
            info.getValueKeyList().add(s);
        }
        return info;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public AlarmRuleInfo findOneRule(int id) {
        AlarmRuleInfo info = new AlarmRuleInfo();

        // 1.告警规则
        AlarmRuleEntity entity = alarmRuleRepository.findOne(id);
        List<String> refUsers = Lists.newArrayList();
        if (entity != null) {
            BeanUtils.copyProperties(entity, info);
            if (entity.getThreshold() > KPI_PRECISION)
                info.setThreshold(String.valueOf(entity.getThreshold()));
            if (entity.getThresholdAnother() > KPI_PRECISION)
                info.setThresholdAnother(String.valueOf(entity.getThresholdAnother()));
            refUsers = refRuleUserRepository.findByRuleId(entity.getId());
        }

        // 3.告警人员(给谁发出告警)
        List<UserEntity> users = userRepository.findAll();
        for(UserEntity ue: users) {
            GroupSelect userSelect = new GroupSelect(ue.getUserId(), ue.getChineseName(), ue.getDep());
            if (refUsers.contains(ue.getUserId()))
                userSelect.setSelected(true);
            info.getUserList().add(userSelect);
        }

        // 4.初始化groupKey,valueKey列表
        StatBean stat = streamManager.findStat(entity.getKpiName());
        for (String value : streamProvider.findGroupByName(entity.getKpiName())) {
            Select s = new Select(value);
            if (entity != null && entity.getGroupKey() != null && entity.getGroupKey().indexOf(value) > -1)
                s.setSelected(true);
            info.getGroupKeyList().add(s);
        }
        for (Object v : stat.getValueKeyList()) {
            Select s = new Select(String.valueOf(v));
            if (entity != null && String.valueOf(v).equals(entity.getValueKey()))
                s.setSelected(true);
            info.getValueKeyList().add(s);
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
            for (Select select: ruleInfo.getGroupKeyList()) {
                String current = ruleEntity.getGroupKey() == null ? "" :ruleEntity.getGroupKey();
                ruleEntity.setGroupKey(current + SEPARATOR_COMMA + select.getValue());
            }
            if (ruleEntity.getGroupKey() != null)
                ruleEntity.setGroupKey(ruleEntity.getGroupKey().substring(1));
            ruleEntity.setMaintainTime(new Timestamp(System.currentTimeMillis()));
            alarmRuleRepository.save(ruleEntity);  //保存规则

            List<RefRuleUser> refList = Lists.newArrayList();
            for (Select user: ruleInfo.getUserList()) {
                refList.add(new RefRuleUser(ruleEntity.getId(), user.getValue()));
            }
            refRuleUserRepository.deleteByRuleId(ruleEntity.getId());
            refRuleUserRepository.save(refList);  //保存规则和用户关联
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Transactional
    @Override
    public void deleteRule(int id) {
        alarmRuleRepository.delete(id);
        refRuleUserRepository.deleteByRuleId(id);
    }

    @Override
    public List<AlarmRuleInfo> findAllAlarmRule() {
        List<AlarmRuleEntity> ruleEntities = alarmRuleRepository.findAll();
        return convertRule(ruleEntities);
    }

    private List<AlarmRuleInfo> convertRule(List<AlarmRuleEntity> ruleEntities) {
        List<AlarmRuleInfo> ruleInfos = Lists.newArrayList();
        if (ruleEntities == null) return ruleInfos;

        for(AlarmRuleEntity entity: ruleEntities) {
            AlarmRuleInfo ruleInfo = new AlarmRuleInfo();
            BeanUtils.copyProperties(entity, ruleInfo);
            if (entity.getThreshold() > KPI_PRECISION)
                ruleInfo.setThreshold(String.valueOf(entity.getThreshold()));
            if (entity.getThresholdAnother() > KPI_PRECISION)
                ruleInfo.setThresholdAnother(String.valueOf(entity.getThresholdAnother()));
            ruleInfo.setMaintainTime(FAST_SDF.format(entity.getMaintainTime()));

            List<UserEntity> refUsers = userRepository.findByRuleId(entity.getId());
            for (UserEntity user: refUsers) {
                ruleInfo.getUserList().add(new Select(user.getUserId(), user.getChineseName()));
            }
            ruleInfos.add(ruleInfo);
        }
        return ruleInfos;
    }

    @Override
    public List<AlarmRuleInfo> findRuleByConfigId(int configId) {
        List<AlarmRuleEntity> ruleEntities = alarmRuleRepository.findByConfigId(configId);
        return convertRule(ruleEntities);
    }

    @Override
    public List<Select> findUserSelect() {
        List<UserEntity> entities = userRepository.findAll();
        List<Select> userList = Lists.newArrayList();
        for (UserEntity entity : entities) {
            GroupSelect select = new GroupSelect(entity.getUserId(), entity.getChineseName(), entity.getDep());
            userList.add(select);
        }
        return userList;
    }
}
