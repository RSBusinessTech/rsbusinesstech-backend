package com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.dashboard.dto;

import lombok.*;

/**
 * Represents the Property Status Chart.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
 public  class PropertyStatusChartDTO {
  private long totalRentalProperties;
  private long totalSaleProperties;
  private long totalCommercialProperties;
  private long totalMm2hProperties;
  private long totalNewProjects;
}

