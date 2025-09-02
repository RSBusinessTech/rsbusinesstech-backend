package com.rsbusinesstech.rsbusinesstech_backend.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailSenderConfig {

    //Bean for RS BusinessTech(rsbusinesstech@gmail.com).
    @Bean(name = "rsBusinessTechMailSender")
    public JavaMailSender rsBusinessTechMailSender() {
        return createMailSender("rsbusinesstech@gmail.com", "pndj fkdq iqrd lfdt");
    }

    //Bean for LashMap Beauty Studio(Lashmap.beautystudio@gmail.com).
    @Bean(name = "lashMapBeautyStudioMailSender")
    public JavaMailSender lashMapBeautyStudioMailSender() {
        return createMailSender("lashmap.beautystudio@gmail.com", "racc bsit ilns bjis");
    }

    private JavaMailSender createMailSender(String username, String password) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost("smtp.gmail.com");
        sender.setPort(587);
        sender.setUsername(username);
        sender.setPassword(password);

        Properties props = sender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return sender;
    }
}
