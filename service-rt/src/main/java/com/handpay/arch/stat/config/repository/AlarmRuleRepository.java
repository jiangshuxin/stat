package com.handpay.arch.stat.config.repository;

import com.handpay.arch.stat.config.model.entity.AlarmRuleEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * Created by fczheng on 2016/11/15.
 */
public interface AlarmRuleRepository extends JpaRepository<AlarmRuleEntity, Integer> {

    AlarmRuleEntity findByKpiShortNameAndConfigId(String kpiShortName, int configId);

    List<AlarmRuleEntity> findByKpiShortNameAndValueKeyIn(String kpiShortName, Collection<String> valueKeys);
}
