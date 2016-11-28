package com.handpay.arch.stat.config.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.handpay.arch.stat.config.model.entity.AlarmRecordEntity;

import java.util.List;

/**
 * Created by fczheng on 2016/11/18.
 */
public interface AlarmRecordRepository extends JpaRepository<AlarmRecordEntity, Integer> {

    @Query("SELECT t" +
            " FROM alarm_record t, alarm_rule r" +
            " WHERE t.ruleId = r.id" +
            " AND r.noticeWay = 'Email'" +
            " AND ( t.sendState = 0 OR (t.sendState = 2 AND t.sendNumber < r.retryTime) )" +
            " ORDER BY t.alarmTime DESC")
    List<AlarmRecordEntity> findFirst10Email();

}
