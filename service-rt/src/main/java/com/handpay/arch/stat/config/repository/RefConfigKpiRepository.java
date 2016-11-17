package com.handpay.arch.stat.config.repository;

import com.handpay.arch.stat.domain.RefConfigKpi;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by fczheng on 2016/11/9.
 */
public interface RefConfigKpiRepository extends JpaRepository<RefConfigKpi, RefConfigKpi> {
    void deleteByConfigId(int configId);
}
