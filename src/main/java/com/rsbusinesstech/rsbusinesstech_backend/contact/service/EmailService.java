package com.rsbusinesstech.rsbusinesstech_backend.contact.service;

import com.rsbusinesstech.rsbusinesstech_backend.contact.model.EmailRequest;
import com.rsbusinesstech.rsbusinesstech_backend.contact.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService
{
    @Autowired
    private JavaMailSender mailSender;

    //method to send email from "Contact" section.
    public void sendEmail(EmailRequest emailRequest){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("rsbusinesstech@gmail.com");                                                                     //setting "to".
        message.setSubject("Enquiry from Customer || "+emailRequest.getName() +" || "+emailRequest.getEmail());       //setting "subject".
        message.setText(
                "You have a new message from your website:\n\n" +                                                     //setting "body".
                 "Message:\n" + emailRequest.getMessage());
        message.setFrom("rsbusinesstech@gmail.com");                                                                  //setting "from".

        mailSender.send(message);
    }
}
