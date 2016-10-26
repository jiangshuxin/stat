package com.handpay.arch.stat.input;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.handpay.arch.stat.bean.StatBean;
import com.handpay.arch.stat.manager.StreamManager;
import com.handpay.arch.stat.output.StreamWriter;

public class TopicListener {

	@Autowired
	private StreamManager manager;
	
	@Autowired
	private StreamWriter writer;

	private ExecutorService executorService = Executors.newFixedThreadPool(100);// FIXME 100

	@PostConstruct
	public void listen() {
		Collection<StatBean> statArray = manager.stats();
		for (StatBean bean : statArray) {
			Consumer c = new Consumer(bean.getResultTopic(), bean.getName());
			c.setWriter(writer);
			executorService.submit(c);
		}
	}
}
