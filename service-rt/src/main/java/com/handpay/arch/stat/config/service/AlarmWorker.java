package com.handpay.arch.stat.config.service;

import com.handpay.arch.stat.bean.CommonResult;
import com.handpay.arch.stat.bean.StatBean;

/**
 * Created by fczheng on 2016/11/17.
 */
public interface AlarmWorker {
    @SuppressWarnings("rawtypes")
	void checkKpi(StatBean statBean, CommonResult result);
}
