package com.handpay.arch.stat.config;

import com.handpay.arch.stat.handler.ChartHandler;
import com.handpay.arch.stat.handler.StatResultHandler;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by fczheng on 2016/10/26.
 */

@Configuration
@EnableAutoConfiguration
@EnableWebSocket
public class WebSocketRegister extends SpringBootServletInitializer
        implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(statResultHandler(), "/websock/echo").withSockJS();
        registry.addHandler(statResultHandler(), "/websock/init.htm").setAllowedOrigins("http://localhost:9001","http://10.48.114.14:9001");
        registry.addHandler(chartHandler(), "/websock/chart/show.htm");
    }

    @Bean
    public StatResultHandler statResultHandler() {
        return new StatResultHandler();
    }

    @Bean
    public ChartHandler chartHandler() {
        return new ChartHandler();
    }
}
