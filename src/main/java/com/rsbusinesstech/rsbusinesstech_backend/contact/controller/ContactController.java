package com.rsbusinesstech.rsbusinesstech_backend.contact.controller;

import com.rsbusinesstech.rsbusinesstech_backend.contact.model.EmailRequest;
import com.rsbusinesstech.rsbusinesstech_backend.email.service.EmailService;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.email.model.RentalPaymentReminderRequest;
import com.rsbusinesstech.rsbusinesstech_backend.scheduler.JsonUploadScheduler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200", "https://rsbusinesstech.com","https://lashmapbeautystudio.com"})
@RestController
@RequestMapping("/contact")
public class ContactController
{
    @Autowired
    EmailService emailService;

    @Autowired
    JsonUploadScheduler jsonUploadScheduler;

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
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
       }
        return ResponseEntity.ok("Email sent successfully...");
    }

    @PostMapping("/sendEmailLashMapBeautyStudio")
    public ResponseEntity<String> sendEmailLashMapBeautyStudio(@RequestBody EmailRequest emailRequest){
        try{
            if(emailRequest != null){
                emailService.sendEmailLashMapBeautyStudio(emailRequest);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
        }
        return ResponseEntity.ok("Email sent successfully...");
    }

    @PostMapping("/sendEmailToTenant")
    public ResponseEntity<String> sendEmailToTenant(@Valid @RequestBody RentalPaymentReminderRequest rentalPaymentReminderRequest){
        try{
            if(rentalPaymentReminderRequest != null){
                //emailService.sendRentalPaymentReminderEmail(rentalPaymentReminderRequest);
                 jsonUploadScheduler.sendRentalPaymentReminderEmails();
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
        }
        return ResponseEntity.ok("Email sent successfully...");
    }
}
