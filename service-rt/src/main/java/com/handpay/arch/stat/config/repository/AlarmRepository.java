package com.handpay.arch.stat.config.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.handpay.arch.stat.config.model.entity.AlarmEntity;

/**
 * Created by fczheng on 2016/11/18.
 */
public interface AlarmRepository extends JpaRepository<AlarmEntity, Integer> {

}
