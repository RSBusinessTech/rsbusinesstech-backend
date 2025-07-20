package com.rsbusinesstech.rsbusinesstech_backend.healthCheck.controller;

import com.rsbusinesstech.rsbusinesstech_backend.contact.model.Company;
import com.rsbusinesstech.rsbusinesstech_backend.contact.model.EmailRequest;
import com.rsbusinesstech.rsbusinesstech_backend.contact.service.CompanyService;
import com.rsbusinesstech.rsbusinesstech_backend.contact.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/healthCheck")
public class CompanyController
{
    @Autowired
    CompanyService companyService;

    @PostMapping("/saveCompany")
    public ResponseEntity<String> saveCompany(@RequestBody Company company){
        String response = "Failed to save company details...";
       try{
          if(company != null){
              response = companyService.saveCompany(company);
          }
       }catch (Exception e){
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
       }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getCompanyEmail")
    public ResponseEntity<String> getCompanyEmail(){
        String response = "Failed to fetch company email...";
        try{
             response = companyService.getCompanyEmail();
        }catch (Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
