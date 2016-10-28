package com.handpay.arch.stat.handler;

import com.alibaba.fastjson.JSON;
import com.handpay.arch.common.ResultVO;
import com.handpay.rache.core.spring.StringRedisTemplateX;
import com.handpay.rache.core.spring.connection.StringRedisConnectionX;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ChartHandler extends TextWebSocketHandler {

	@Override
	public void handleTextMessage(final WebSocketSession session, final TextMessage message) throws Exception {
		System.out.println("test ..................");


		ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				String in = null;
				try {
					URL url = new URL("http://localhost:8080/show-rt/mock/linearea");
					URLConnection urlCon = url.openConnection();
					urlCon.setConnectTimeout(2000);
					urlCon.setReadTimeout(2000);
					InputStream input = urlCon.getInputStream();
					in = IOUtils.toString(input);
					input.close();
					session.sendMessage(new TextMessage(in));
					System.out.println(new Date());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}, 0L, 3000L, TimeUnit.MILLISECONDS);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {


		//session.sendMessage(new TextMessage(JSON.toJSONString(ResultVO.buildSucResult())));
	}
}
