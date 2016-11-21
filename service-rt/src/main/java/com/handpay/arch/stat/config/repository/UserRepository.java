package com.handpay.arch.stat.config.repository;

import com.handpay.arch.stat.config.model.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by fczheng on 2016/11/11.
 */
public interface UserRepository extends JpaRepository<UserEntity, String> {

}
