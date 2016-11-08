package com.handpay.arch.stat.config.repository;


import com.handpay.arch.stat.domain.ConfigEntity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by fczheng on 2016/10/31.
 */
public interface ConfigInfoRepository extends JpaRepository<ConfigEntity, Integer> {

}
