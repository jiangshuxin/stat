package com.handpay.arch.stat.config.repository;


import com.handpay.arch.stat.config.model.entity.ConfigEntity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by fczheng on 2016/10/31.
 */
public interface ConfigEntityRepository extends JpaRepository<ConfigEntity, Integer> {

}
