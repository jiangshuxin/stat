package com.handpay.arch.stat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Created by fczheng on 2016/10/26.
 */

@SpringBootApplication
@RestController
@ImportResource(locations = {"classpath:spring/spring-context.xml"})
public class ShowApplication {

    @GetMapping("/common/{value}")
    public String test(@PathVariable String value) {
        return "test is ok "+value;
    }

    public static void main(String[] args) {
        SpringApplication.run(ShowApplication.class, args);
    }

}
