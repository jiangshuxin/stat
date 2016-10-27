package com.handpay.arch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fczheng on 2016/10/27.
 */

@SpringBootApplication
@RestController
@ImportResource(locations = {"classpath:spring/spring-application.xml"})
public class ServiceAplication {
    @Autowired
    private Environment env;

    @GetMapping("/test")
    public String test() {
        return env.getProperty("zkName", String.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceAplication.class, args);
    }
}
