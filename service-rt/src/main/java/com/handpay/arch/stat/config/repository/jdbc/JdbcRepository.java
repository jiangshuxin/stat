package com.handpay.arch.stat.config.repository.jdbc;

import com.handpay.arch.stat.config.model.entity.ConfigEntity;

import java.util.List;

/**
 * Created by fczheng on 2016/11/11.
 * 复杂sql执行接口
 */
public interface JdbcRepository {
    List<ConfigEntity> findAllConfig();
}
