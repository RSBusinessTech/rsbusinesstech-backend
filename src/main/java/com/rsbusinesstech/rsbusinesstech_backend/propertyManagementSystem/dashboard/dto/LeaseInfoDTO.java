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

    //Property Info
    private Long propertyId;
    private String propertyName;
    private String propertyType;
    private String expiryDate;
}

