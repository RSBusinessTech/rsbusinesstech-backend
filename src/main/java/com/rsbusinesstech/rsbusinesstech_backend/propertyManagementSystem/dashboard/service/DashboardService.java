package com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.dashboard.service;

import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.customer.service.CustomerService;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.dashboard.dto.LeaseInfoDTO;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.dashboard.dto.PMSDashboardSummaryDTO;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.dashboard.dto.PropertyStatusChartDTO;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.owner.service.OwnerService;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.property.dto.PropertyDTO;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.property.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DashboardService {

    @Autowired
    PropertyService propertyService;

    @Autowired
    CustomerService customerService;

    @Autowired
    OwnerService ownerService;

    public PMSDashboardSummaryDTO getPMSDashboardSummary(String agentId){
        PMSDashboardSummaryDTO pmsDashboardSummaryDTO = new PMSDashboardSummaryDTO();
        PropertyStatusChartDTO propertyStatusChartDTO = new PropertyStatusChartDTO();

        Map<String,Long> customersInfoMap = customerService.getCustomersInfo(agentId);
        Map<String,Long> ownersInfoMap = ownerService.getOwnersInfo(agentId);
        Map<String,Object> rentedPropertiesInfo = propertyService.getPropertiesInfoByType("rent", agentId);
        Map<String,Object> salesPropertiesInfo = propertyService.getPropertiesInfoByType("buy", agentId);

        List<PropertyDTO> allRentalProperties = (List<PropertyDTO>) rentedPropertiesInfo.getOrDefault("totalProperties", Collections.emptyList());
        List<PropertyDTO> rentedOutProperties = (List<PropertyDTO>) rentedPropertiesInfo.getOrDefault("occupiedProperties", Collections.emptyList());
        List<PropertyDTO> toBeRentedProperties = (List<PropertyDTO>) rentedPropertiesInfo.getOrDefault("vacantProperties", Collections.emptyList());

        List<PropertyDTO> allSalesProperties = (List<PropertyDTO>) salesPropertiesInfo.getOrDefault("totalProperties", Collections.emptyList());
        List<PropertyDTO> soldOutProperties = (List<PropertyDTO>) salesPropertiesInfo.getOrDefault("occupiedProperties", Collections.emptyList());
        List<PropertyDTO> toBeSoldProperties = (List<PropertyDTO>) salesPropertiesInfo.getOrDefault("vacantProperties", Collections.emptyList());

        List<LeaseInfoDTO> pendingRentalsThisMonth = new ArrayList<>();
        List<LeaseInfoDTO> contractsExpiringThisMonth = new ArrayList<>();
        List<LeaseInfoDTO> propertiesRentedThisMonth = new ArrayList<>();
        List<LeaseInfoDTO> propertiesSoldThisMonth = new ArrayList<>();

        //Property Info
        pmsDashboardSummaryDTO.setTotalProperties(propertyService.getAllPropertiesCount(agentId));

        pmsDashboardSummaryDTO.setTotalRentalProperties(allRentalProperties.size());
        pmsDashboardSummaryDTO.setTotalRentedOutProperties(rentedOutProperties.size()); //rented out properties
        pmsDashboardSummaryDTO.setTotalToBeRentedProperties(toBeRentedProperties.size()); //to be rented properties

        pmsDashboardSummaryDTO.setTotalSaleProperties(allSalesProperties.size());
        pmsDashboardSummaryDTO.setTotalSoldOutProperties(soldOutProperties.size());  //sold out properties
        pmsDashboardSummaryDTO.setTotalToBeSoldProperties(toBeSoldProperties.size());  //to be sold properties

        pmsDashboardSummaryDTO.setTotalCommercialProperties(propertyService.getPropertiesCountByType("commercial", agentId));
        pmsDashboardSummaryDTO.setTotalMm2hProperties(pmsDashboardSummaryDTO.getTotalSaleProperties());
        pmsDashboardSummaryDTO.setTotalNewProjects(propertyService.getPropertiesCountByType("newprojects", agentId));

        //Property Customer Info
        pmsDashboardSummaryDTO.setTotalTenants(customersInfoMap.getOrDefault("totalTenants",0L));
        pmsDashboardSummaryDTO.setTotalBuyers(customersInfoMap.getOrDefault("totalBuyers", 0L));
        pmsDashboardSummaryDTO.setTotalOwners(ownersInfoMap.getOrDefault("totalOwners",0L));

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
