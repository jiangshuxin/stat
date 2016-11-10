package com.handpay.arch.stat.handler;

import com.alibaba.fastjson.JSON;
import com.handpay.arch.common.ResultVO;
import com.handpay.rache.core.spring.StringRedisTemplateX;
import com.handpay.rache.core.spring.connection.StringRedisConnectionX;

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
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class StatResultHandler extends TextWebSocketHandler {

	@Autowired
	private StringRedisTemplateX stringRedisTemplateX;

	@Override
	public void handleTextMessage(final WebSocketSession session, final TextMessage message) throws Exception {
		System.out.println("test ..................");


		String payload = message.getPayload();
		final List<String> topicList = JSON.parseArray(payload,String.class);
		for(final String topic : topicList){
			new Thread(new Runnable() {
				@Override
				public void run() {
					stringRedisTemplateX.execute(new RedisCallback<String>() {
						@Override
						public String doInRedis(RedisConnection connection) throws DataAccessException {
							final StringRedisConnectionX conn = (StringRedisConnectionX)connection;


								conn.subscribe(new MessageListener(){
									@Override
									public void onMessage(Message message, byte[] pattern) {
										try {
											session.sendMessage(new TextMessage(new String(message.getBody())));
										} catch (IOException e) {
											e.printStackTrace();
										}
									}}, topic);

							return null;
						}
					});
				}
			}).start();
		}
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		//new Consumer("user-behavior-topic-result",session).start();
		session.sendMessage(new TextMessage(JSON.toJSONString(ResultVO.buildSucResult())));
	}
}
