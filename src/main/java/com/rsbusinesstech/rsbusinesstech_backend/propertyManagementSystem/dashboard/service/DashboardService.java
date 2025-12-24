package com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.dashboard.service;

import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.customer.service.CustomerService;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.dashboard.dto.LeaseInfoDTO;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.dashboard.dto.PMSDashboardSummaryDTO;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.dashboard.dto.PropertyStatusChartDTO;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.property.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    PropertyService propertyService;

    @Autowired
    CustomerService customerService;

    public PMSDashboardSummaryDTO getPMSDashboardSummary(){
        PMSDashboardSummaryDTO pmsDashboardSummaryDTO = new PMSDashboardSummaryDTO();
        PropertyStatusChartDTO propertyStatusChartDTO = new PropertyStatusChartDTO();
        Map<String,Long> customersInfoMap = customerService.getCustomersInfo();

        List<LeaseInfoDTO> pendingRentalsThisMonth = new ArrayList<>();
        List<LeaseInfoDTO> contractsExpiringThisMonth = new ArrayList<>();
        List<LeaseInfoDTO> propertiesRentedThisMonth = new ArrayList<>();
        List<LeaseInfoDTO> propertiesSoldThisMonth = new ArrayList<>();

        //Property Info
        pmsDashboardSummaryDTO.setTotalProperties(propertyService.getAllPropertiesCount());
        pmsDashboardSummaryDTO.setTotalRentalProperties(propertyService.getPropertiesCountByType("rent"));
        pmsDashboardSummaryDTO.setTotalSaleProperties(propertyService.getPropertiesCountByType("buy"));
        pmsDashboardSummaryDTO.setTotalCommercialProperties(propertyService.getPropertiesCountByType("commercial"));
        pmsDashboardSummaryDTO.setTotalMm2hProperties(pmsDashboardSummaryDTO.getTotalSaleProperties());
        pmsDashboardSummaryDTO.setTotalNewProjects(propertyService.getPropertiesCountByType("newprojects"));

        //Property Customer Info
        pmsDashboardSummaryDTO.setTotalTenants(customersInfoMap.getOrDefault("totalTenants",0L));
        pmsDashboardSummaryDTO.setTotalBuyers(customersInfoMap.getOrDefault("totalBuyers", 0L));
        pmsDashboardSummaryDTO.setTotalOwners(0);

        //Lists
        pmsDashboardSummaryDTO.setPendingRentalsThisMonth(pendingRentalsThisMonth);
        pmsDashboardSummaryDTO.setContractsExpiringThisMonth(contractsExpiringThisMonth);
        pmsDashboardSummaryDTO.setPropertiesRentedThisMonth(propertiesRentedThisMonth);
        pmsDashboardSummaryDTO.setPropertiesSoldThisMonth(propertiesSoldThisMonth);

        propertyStatusChartDTO.setOccupied(12);
        propertyStatusChartDTO.setVacant(20);
        propertyStatusChartDTO.setUnderMaintenance(2);
        propertyStatusChartDTO.setReserved(5);
        pmsDashboardSummaryDTO.setPropertyStatusChart(propertyStatusChartDTO);

        return pmsDashboardSummaryDTO;
    }
}
