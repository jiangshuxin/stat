package com.handpay.arch.stat.config.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.handpay.arch.stat.bean.StatBean;
import com.handpay.arch.stat.rpc.DubboRTResult;
import com.handpay.arch.stat.rpc.DubboTable;
import com.handpay.rache.core.spring.StringRedisTemplateX;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlarmWorkerTester {
	@Autowired
	private AlarmWorker worker;
	private StatBean<DubboTable, DubboRTResult> statBean;
	private DubboRTResult result;
	
	@Autowired
    private StringRedisTemplateX stringRedisTemplateX;

	@Before
	public void setUp(){
		statBean = new StatBean<DubboTable, DubboRTResult>();
		statBean.setName("dubboRT");
		statBean.setResultClass(DubboRTResult.class);
		
		//10.48.194.26|com.handpay.auth.service.MenuService|queryByUserIdWithParent
		result = new DubboRTResult();
		result.setServerHost("10.48.194.26");
		result.setClassName("com.handpay.auth.service.MenuService");
		result.setMethodName("queryByUserIdWithParent");
		result.setResponseTime("120001");
	}
	
    @Test
    public void testRedisZSet() {
        stringRedisTemplateX.boundZSetOps("dubboRT|GroupKeySet").range(0, -1);
        stringRedisTemplateX.boundZSetOps("dubboQPS|GroupKeySet").range(0, -1);
    }
	
	@Test
	public void testCheck() {
		worker.checkKpi(statBean, result);
	}
	
}
