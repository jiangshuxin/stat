package com.handpay.arch.stat.config.repository;



import com.handpay.arch.stat.config.model.entity.MetricKpiEntity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by fczheng on 2016/11/7.
 */
public interface KpiRepository extends JpaRepository<MetricKpiEntity, String> {
}
