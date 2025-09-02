package com.rsbusinesstech.rsbusinesstech_backend.contact.service;

import com.rsbusinesstech.rsbusinesstech_backend.contact.model.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService
{
//    @Autowired
//    private JavaMailSender mailSender;

    private final JavaMailSender rsBusinessTechMailSender;
    private final JavaMailSender lashMapBeautyStudioMailSender;

    public EmailService(@Qualifier("rsBusinessTechMailSender") JavaMailSender rsBusinessTechMailSender,
                        @Qualifier("lashMapBeautyStudioMailSender") JavaMailSender lashMapBeautyStudioMailSender) {
        this.rsBusinessTechMailSender = rsBusinessTechMailSender;
        this.lashMapBeautyStudioMailSender = lashMapBeautyStudioMailSender;
    }

    //method to send email to RS BusinessTech from "Contact" section.
    public void sendEmail(EmailRequest emailRequest){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("rsbusinesstech@gmail.com");                                                                     //setting "to".
        message.setSubject("Enquiry from Customer || "+emailRequest.getName() +" || "+emailRequest.getEmail());       //setting "subject".
        message.setText(
                "You have a new message from your website:\n\n" +                                                     //setting "body".
                 "Message:\n" + emailRequest.getMessage());
        message.setFrom("rsbusinesstech@gmail.com");                                                                  //setting "from".

//        mailSender.send(message);
        rsBusinessTechMailSender.send(message);
    }

    //method to send email to LashMapBeautyStudio from "Contact" section.
    public void sendEmailLashMapBeautyStudio(EmailRequest emailRequest){
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo("lashmap.beautystudio@gmail.com");                                                                     //setting "to".
        message.setSubject("Enquiry from Customer || "+emailRequest.getName() +" || "+emailRequest.getEmail());       //setting "subject".
        message.setText(
                "You have a new message from your website:\n\n" +                                                     //setting "body".
                        "Message:\n" + emailRequest.getMessage());
        message.setFrom("lashmap.beautystudio@gmail.com");                                                                  //setting "from".

        lashMapBeautyStudioMailSender.send(message);
    }
}
