package com.handpay.arch.stat.config.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.handpay.arch.stat.bean.CommonResult;
import com.handpay.arch.stat.bean.StatBean;
import com.handpay.arch.stat.config.model.MetricChecker;
import com.handpay.arch.stat.config.model.entity.AlarmRecordEntity;
import com.handpay.arch.stat.config.model.entity.AlarmRuleEntity;
import com.handpay.arch.stat.config.repository.AlarmRecordRepository;
import com.handpay.arch.stat.config.repository.AlarmRuleRepository;
import com.handpay.arch.stat.config.service.AlarmWorker;

/**
 * Created by fczheng on 2016/11/17.
 */
@Service
public class AlarmWorkerImpl implements AlarmWorker {
	@Autowired
	private AlarmRuleRepository ruleRepository;
	@Autowired
	private AlarmRecordRepository alarmRepository;

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
		List<AlarmRecordEntity> records = checker.getRecords();
		// 4.将预警数据信息写入数据库
		alarmRepository.save(records);
	}

}
