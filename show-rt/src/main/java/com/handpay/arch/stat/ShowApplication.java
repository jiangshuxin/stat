package com.handpay.arch.stat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by fczheng on 2016/10/26.
 */

@SpringBootApplication
@ImportResource(locations = {"classpath:spring/spring-context.xml"})
public class ShowApplication {

    @GetMapping("/test")
    public String test() {
        return "test is ok";
    }

//    @Bean
//    public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
//        ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
//        registration.addUrlMappings("/websock/*");
//        return registration;
//    }

    public static void main(String[] args) {
        SpringApplication.run(ShowApplication.class, args);
    }

}
