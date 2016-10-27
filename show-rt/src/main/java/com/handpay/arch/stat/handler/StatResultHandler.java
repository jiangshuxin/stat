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

public class StatResultHandler extends TextWebSocketHandler {

	@Autowired
	private StringRedisTemplateX stringRedisTemplateX;

	@Override
	public void handleTextMessage(final WebSocketSession session, final TextMessage message) throws Exception {
		System.out.println("test ..................");

		session.sendMessage(new TextMessage("hello"));
		
		stringRedisTemplateX.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				StringRedisConnectionX conn = (StringRedisConnectionX)connection;
				conn.subscribe(new MessageListener(){
					@Override
					public void onMessage(Message message, byte[] pattern) {
						try {
							session.sendMessage(new TextMessage(new String(message.getBody())));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}}, message.getPayload());
				return null;
			}
		});
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		//new Consumer("user-behavior-topic-result",session).start();
		session.sendMessage(new TextMessage(JSON.toJSONString(ResultVO.buildSucResult())));
	}
}
