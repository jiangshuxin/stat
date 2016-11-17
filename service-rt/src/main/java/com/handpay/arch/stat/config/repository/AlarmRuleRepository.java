package com.handpay.arch.stat.config.repository;

import com.handpay.arch.stat.domain.AlarmRule;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by fczheng on 2016/11/15.
 */
public interface AlarmRuleRepository extends JpaRepository<AlarmRule, Integer> {
    AlarmRule findByKpiShortNameAndConfigId(String kpiShortName, int configId);
}
