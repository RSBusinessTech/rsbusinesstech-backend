package com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.dashboard.dto;

import lombok.*;

import java.util.List;

/**
 * Represents the full dashboard summary including KPIs(Key Performance Indicator), lists, and chart data.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PMSDashboardSummaryDTO {

    // =================== KPIs ===================
    private int totalProperties;
    private int totalRentalProperties;
    private int totalSaleProperties;
    private int totalCommercialProperties;
    private int totalMm2hProperties;
    private int totalNewProjects;
    private int totalTenants;
    private int totalBuyers;
    private int totalOwners;

    // =================== Lists ===================
    private List<LeaseInfoDTO> pendingRentalsThisMonth;
    private List<LeaseInfoDTO> contractsExpiringThisMonth;
    private List<LeaseInfoDTO> propertiesRentedThisMonth;
    private List<LeaseInfoDTO> propertiesSoldThisMonth;

    private PropertyStatusChartDTO propertyStatusChart;
}

