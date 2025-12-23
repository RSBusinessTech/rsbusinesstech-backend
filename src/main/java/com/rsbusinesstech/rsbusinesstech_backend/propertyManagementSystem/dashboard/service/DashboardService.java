package com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.dashboard.service;

import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.dashboard.dto.LeaseInfoDTO;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.dashboard.dto.PMSDashboardSummaryDTO;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.dashboard.dto.PropertyStatusChartDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {
    public PMSDashboardSummaryDTO getPMSDashboardSummary(){
        PMSDashboardSummaryDTO pmsDashboardSummaryDTO = new PMSDashboardSummaryDTO();
        PropertyStatusChartDTO propertyStatusChartDTO = new PropertyStatusChartDTO();

        List<LeaseInfoDTO> pendingRentalsThisMonth = new ArrayList<>();
        List<LeaseInfoDTO> contractsExpiringThisMonth = new ArrayList<>();
        List<LeaseInfoDTO> propertiesRentedThisMonth = new ArrayList<>();
        List<LeaseInfoDTO> propertiesSoldThisMonth = new ArrayList<>();

        //Property Info
        pmsDashboardSummaryDTO.setTotalProperties(20);
        pmsDashboardSummaryDTO.setTotalRentalProperties(12);
        pmsDashboardSummaryDTO.setTotalSaleProperties(8);
        pmsDashboardSummaryDTO.setTotalCommercialProperties(0);
        pmsDashboardSummaryDTO.setTotalMm2hProperties(8);
        pmsDashboardSummaryDTO.setTotalNewProjects(10);

        //Property Customer Info
        pmsDashboardSummaryDTO.setTotalTenants(12);
        pmsDashboardSummaryDTO.setTotalBuyers(5);
        pmsDashboardSummaryDTO.setTotalOwners(30);

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
