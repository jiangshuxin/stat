package com.handpay.arch.stat.config.repository;

import com.handpay.arch.stat.config.model.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by fczheng on 2016/11/11.
 */
public interface UserRepository extends JpaRepository<UserEntity, String> {
    /*
    * @Entity(name="xxx"), xxx为@Query注解中的表
    * */
    @Query("select u from user u, ref_rule_user r where u.userId = r.userId and r.ruleId=?1")
    List<UserEntity> findByRuleId(int id);
}
