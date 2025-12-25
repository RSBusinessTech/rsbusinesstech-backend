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
    private long totalProperties;
    private long totalRentalProperties;
    private long totalRentedOutProperties;
    private long totalToBeRentedProperties;
    private long totalSaleProperties;
    private long totalSoldOutProperties;
    private long totalToBeSoldProperties;
    private long totalCommercialProperties;
    private long totalMm2hProperties;
    private long totalNewProjects;
    private long totalTenants;
    private long totalBuyers;
    private long totalOwners;

    // =================== Lists ===================
    private List<LeaseInfoDTO> pendingRentalsThisMonth;
    private List<LeaseInfoDTO> contractsExpiringThisMonth;
    private List<LeaseInfoDTO> propertiesRentedThisMonth;
    private List<LeaseInfoDTO> propertiesSoldThisMonth;

    private PropertyStatusChartDTO propertyStatusChart;
}

