package com.handpay.arch.stat.output;

import com.handpay.arch.stat.bean.CommonResult;

public interface StreamWriter {

	void write(String statName,CommonResult... unit);
}
