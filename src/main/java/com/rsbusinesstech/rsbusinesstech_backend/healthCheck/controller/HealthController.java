package com.rsbusinesstech.rsbusinesstech_backend.healthCheck.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthCheck")
public class HealthController
{
    @GetMapping("/checkBackendConnectivity")
    public String checkBackendConnectivity(){
        return "Backend connected successfully.";
    }
}
