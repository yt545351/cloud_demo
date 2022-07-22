package com.example.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@Slf4j
public class    LogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class, args);
        log.info("----------------Log启动---------------------");
    }

}
