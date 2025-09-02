package com.rsbusinesstech.rsbusinesstech_backend.booking.controller;

import com.rsbusinesstech.rsbusinesstech_backend.booking.model.BookingRequest;
import com.rsbusinesstech.rsbusinesstech_backend.booking.service.BookingEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200", "https://rsbusinesstech.com","https://lashmapbeautystudio.com"})
@RestController
@RequestMapping("/booking")
public class BookingController
{
    @Autowired
    BookingEmailService bookingEmailService;

    /*
      springBoot uses your gmail account(rsbusinesstech@gmail.com) to send an email to yourself(rsbusinesstech@gmail.com).
      The user’s email (x@gmail.com) is not the sender, it’s part of the email content.
    * */

    @PostMapping("/sendBookingEmail")
    public ResponseEntity<String> sendBookingEmail(@RequestBody BookingRequest bookingRequest){
       try{
          if(bookingRequest != null){
              bookingEmailService.sendBookingEmail(bookingRequest);
          }
       }catch (Exception e){
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email...");
       }
        return ResponseEntity.ok("Email sent successfully...");
    }

    @PostMapping("/sendBookingEmailLashMapBeautyStudio")
    public ResponseEntity<String> sendBookingEmailLashMapBeautyStudio(@RequestBody BookingRequest bookingRequest){
        try{
            if(bookingRequest != null){
                bookingEmailService.sendBookingEmailLashMapBeautyStudio(bookingRequest);
            }
        }catch (Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
        }
        return ResponseEntity.ok("Email sent successfully...");
    }
}
