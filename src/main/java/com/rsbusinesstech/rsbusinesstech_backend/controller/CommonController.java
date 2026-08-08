package com.rsbusinesstech.rsbusinesstech_backend.controller;

import com.rsbusinesstech.rsbusinesstech_backend.booking.model.BookingRequest;
import com.rsbusinesstech.rsbusinesstech_backend.booking.service.BookingEmailService;
import com.rsbusinesstech.rsbusinesstech_backend.email.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200", "https://www.rsbusinesstech.com","https://www.lashmapbeautystudio.com",
                         "https://www.vyenpropertyadvisor.com/","https://www.anaangproperty.com","https://www.1pavilionsquareklcc.com"})
@RestController
@RequestMapping("/common")
public class CommonController
{
    @Autowired
    EmailService emailService;

    /*
      springBoot uses your gmail account(rsbusinesstech@gmail.com) to send an email to yourself(rsbusinesstech@gmail.com).
      The user’s email (x@gmail.com) is not the sender, it’s part of the email content.
    * */
    @GetMapping("/trackTraffic/{ownerEmail}")
    public ResponseEntity<String> trackTraffic(HttpServletRequest request, @PathVariable String ownerEmail){
        emailService.trackTraffic(request, ownerEmail);

        return ResponseEntity.ok("Visitor Tracked SUCCESSFULLY");
    }
}
