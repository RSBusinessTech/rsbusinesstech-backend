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
  private int occupied;
  private int vacant;
  private int underMaintenance;
  private int reserved; // Booked but not yet occupied.
}

