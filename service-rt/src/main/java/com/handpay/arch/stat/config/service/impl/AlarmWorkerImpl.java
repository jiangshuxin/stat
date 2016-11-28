package com.handpay.arch.stat.config.service.impl;

import static com.handpay.arch.common.Constants.EMAIL_HOST;

import java.sql.Timestamp;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.handpay.arch.stat.bean.CommonResult;
import com.handpay.arch.stat.bean.StatBean;
import com.handpay.arch.stat.config.model.MetricChecker;
import com.handpay.arch.stat.config.model.entity.AlarmRecordEntity;
import com.handpay.arch.stat.config.model.entity.AlarmRuleEntity;
import com.handpay.arch.stat.config.model.entity.UserEntity;
import com.handpay.arch.stat.config.repository.AlarmRecordRepository;
import com.handpay.arch.stat.config.repository.AlarmRuleRepository;
import com.handpay.arch.stat.config.repository.UserRepository;
import com.handpay.arch.stat.config.service.AlarmWorker;

/**
 * Created by fczheng on 2016/11/17.
 */
@Service
@EnableScheduling
public class AlarmWorkerImpl implements AlarmWorker {
    private static final String SUBJECT_PREFIX = "监控告警: ";
    private static final String SENDER = "metric-alarm@99wuxian.com";
    private static Logger log = LoggerFactory.getLogger(AlarmWorker.class);

    @Autowired
    private AlarmRuleRepository ruleRepository;
    @Autowired
    private AlarmRecordRepository alarmRepository;
    @Autowired
    private UserRepository userRepository;

    @SuppressWarnings("rawtypes")
    @Override
    public void checkKpi(StatBean statBean, CommonResult result) {
        if (result == null)
            return;
        MetricChecker checker = new MetricChecker(statBean.getResultClass(), result);

        // 1.分析监控数据
        checker.refine();

        // 2.取出监控数据相关规则
        List<AlarmRuleEntity> ruleList = ruleRepository.findByKpiNameAndValueKeyIn(statBean.getName(), checker.getValueMap().keySet());

        // 3.根据规则判断是否预警
        checker.check(ruleList);

        // 4.将预警数据信息写入数据库
        alarmRepository.save(checker.getRecords());
    }

    @Scheduled(cron = "0 0/5 9-18 * * MON-FRI")
    @Override
    public void sendAlarm() {
        log.info("Send Email start ......");
        List<AlarmRecordEntity> records = alarmRepository.findFirst10Email();
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(EMAIL_HOST);

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            for (AlarmRecordEntity entity : records) {
                List<UserEntity> userEntities = userRepository.findByRuleId(entity.getRuleId());
                if (userEntities == null || userEntities.isEmpty()) 
                    continue;
                helper.setFrom(SENDER);
                helper.setTo(refineAddresses(userEntities));
                helper.setSubject(SUBJECT_PREFIX + entity.getId());
                helper.setText(entity.getContent());
                sender.send(message);
            }
            assembleUpdateAlarmRecord(records, 1);
        } catch (MessagingException e) {
            assembleUpdateAlarmRecord(records, 2);
            e.printStackTrace();
        }
        alarmRepository.save(records);
        log.info("Send Email End ......");
    }

    /*
    * 修改告警记录信息
    * */
    private void assembleUpdateAlarmRecord(List<AlarmRecordEntity> records, int sendState) {
        for(AlarmRecordEntity entity : records) {
            entity.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            entity.setSendState(sendState);
            entity.setSendNumber(entity.getSendNumber() + 1);
        }
    }

    /*
    * 从用户列表中提炼email地址
    * */
    private String[] refineAddresses(List<UserEntity> users) {
        String[] addresses = new String[users.size()];
        for (int i = 0; i < users.size(); i++)
            addresses[i] = users.get(i).getEmail();
        return addresses;
    }

}
