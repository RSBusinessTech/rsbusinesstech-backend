package com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents lease information for a property.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaseInfoDTO {
    //Tenant Info
    private Long tenantId;
    private String tenantName;
    private String tenantWhatsappNumber;

    //Property Info
    private Long propertyId;
    private String propertyName;

    //Rental Info
    private String rentalStartDate;
    private Double rentalAmount;

    //Contract Info
    private Integer rentalDurationInMonths;
    private String contractStartDate;
    private String contractEndDate;
}

