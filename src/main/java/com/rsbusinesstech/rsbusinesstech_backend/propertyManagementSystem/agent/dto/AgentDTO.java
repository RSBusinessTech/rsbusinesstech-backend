package com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgentDTO {

    // Basic Identification
    private String id;
    private String fullName;

    // Contact Information
    private String email;
    private String mobileNumber;
    private String alternatePhoneNumber;
    private String whatsappNumber;

    // Address Details
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    // Preferences / Other Info
    private String preferredContactMethod; // "Email", "Phone", "Whatsapp"
    private String gender; // "Male", "Female", "Other"
    private String imageUrl;
    private String imagePublicId;  //to delete images from clouds.

    // System Metadata
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;

}

