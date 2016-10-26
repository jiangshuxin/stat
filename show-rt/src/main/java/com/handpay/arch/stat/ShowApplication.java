package com.handpay.arch.stat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by fczheng on 2016/10/26.
 */

@SpringBootApplication
@ImportResource(locations = {"classpath:spring/spring-context.xml", "classpath:spring/spring-web.xml"})
public class ShowApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShowApplication.class, args);
    }

}
