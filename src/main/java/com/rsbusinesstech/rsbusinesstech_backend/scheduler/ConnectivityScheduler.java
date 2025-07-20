package com.rsbusinesstech.rsbusinesstech_backend.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/* Creating this Scheduler class in order to schedule this method to be executed after every 14 minutes
   in order to prevent render's "cold-starts" or "service-sleeping", then no need to wait about 50sec to restart it.*/

@Component
public class ConnectivityScheduler
{

    @Autowired
    RestTemplate restTemplate;

    //14min = 840000millis.
    @Scheduled(fixedRate = 840000)
    public void run(){
        restTemplate.getForObject("https://rsbusinesstech-backend.onrender.com/healthCheck/checkBackendConnectivity",String.class);
    }
}
