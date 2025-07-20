package com.rsbusinesstech.rsbusinesstech_backend.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/* Creating this Scheduler class in order to schedule this method to be executed after every 14 minutes
   so that Render cann't shut down the backend server, and then no need to wait about 50sec to restart it.*/

@Component
public class ConnectivityScheduler
{
    //14min = 840000millis.
    @Scheduled(fixedRate = 840000)
    public void run(){
    }
}
