package com.deveye.rabbitmq.sms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.deveye.rabbitmq.sms.mapper")
@EnableScheduling
public class RabbitmqSmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitmqSmsApplication.class, args);
    }

}
