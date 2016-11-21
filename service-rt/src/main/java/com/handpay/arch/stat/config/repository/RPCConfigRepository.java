package com.handpay.arch.stat.config.repository;


import com.handpay.arch.stat.config.model.entity.RPCConfigEntity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by fczheng on 2016/11/1.
 */
public interface RPCConfigRepository extends JpaRepository<RPCConfigEntity, Integer> {

}
