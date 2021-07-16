package com.ibm.mq.correlation.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class IbmMqApplication {

    public static void main(String[] args) {
        SpringApplication.run(IbmMqApplication.class, args);
    }

}
