package com.t1.mail_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MailApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailApiApplication.class, args);
    }

}
