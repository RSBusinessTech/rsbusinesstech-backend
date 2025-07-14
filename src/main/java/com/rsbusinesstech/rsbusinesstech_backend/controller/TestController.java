package com.rsbusinesstech.rsbusinesstech_backend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:4200","https://www.rsbusinesstech.com"})
@RestController
@RequestMapping("/healthCheck")
public class TestController
{
    @GetMapping("/checkBackendConnectivity")
    public String checkBackendConnectivity(){
        return "Backend connected successfully.";
    }
}
