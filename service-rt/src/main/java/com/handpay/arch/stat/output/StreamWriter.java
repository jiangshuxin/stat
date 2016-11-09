package com.handpay.arch.stat.output;

import com.handpay.arch.stat.bean.CommonResult;
import com.handpay.arch.stat.bean.StatBean;

public interface StreamWriter {

	void write(StatBean statBean, CommonResult... unit);
}
