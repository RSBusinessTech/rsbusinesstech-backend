package com.rsbusinesstech.rsbusinesstech_backend.contact.controller;

import com.rsbusinesstech.rsbusinesstech_backend.contact.model.EmailRequest;
import com.rsbusinesstech.rsbusinesstech_backend.contact.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200", "https://rsbusinesstech.com"})
@RestController
@RequestMapping("/contact")
public class ContactController
{
    @Autowired
    EmailService emailService;

    /*
      springBoot uses your gmail account(rsbusinesstech@gmail.com) to send an email to yourself(rsbusinesstech@gmail.com).
      The user’s email (x@gmail.com) is not the sender, it’s part of the email content.
    * */

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest){
       try{
          if(emailRequest != null){
              emailService.sendEmail(emailRequest);
          }
       }catch (Exception e){
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
       }
        return ResponseEntity.ok("Email sent successfully...");
    }
}
