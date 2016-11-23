package com.handpay.arch.stat.config.repository;

import com.handpay.arch.stat.config.model.entity.RefRuleUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by fczheng on 2016/11/15.
 */
public interface RefRuleUserRepository  extends JpaRepository<RefRuleUser, RefRuleUser> {
    @Query("select u.userId from #{#entityName} u where u.ruleId = ?1")
    List<String> findByRuleId(int ruleId);

    void deleteByRuleId(int ruleId);
}
