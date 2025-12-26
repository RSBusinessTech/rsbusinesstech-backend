package com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.dashboard.controller;

import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.dashboard.dto.PMSDashboardSummaryDTO;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.dashboard.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200", "https://rsbusinesstech.com"})
@RestController
@RequestMapping("/propertyManagementSystem")
public class DashboardController {
    @Autowired
    DashboardService dashboardService;

    @GetMapping("/getPMSDashboardSummary/{agentId}")
    public ResponseEntity<PMSDashboardSummaryDTO> getPropertyByType(@PathVariable String agentId){
        PMSDashboardSummaryDTO pmsDashboardSummaryDTO = null;
        try{
            pmsDashboardSummaryDTO = dashboardService.getPMSDashboardSummary(agentId);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pmsDashboardSummaryDTO);
        }
        return ResponseEntity.ok(pmsDashboardSummaryDTO);
    }
}
