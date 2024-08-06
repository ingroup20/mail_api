package com.t1.mail_api.config;

import com.t1.mail_api.service.MailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class MailConfig {

    @Bean
    MailService mailService(JavaMailSender mailSender) {
        return new MailService(mailSender);

    }

}
