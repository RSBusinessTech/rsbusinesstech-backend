package com.rsbusinesstech.rsbusinesstech_backend.customer.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    // Basic Identification
    private Long id;
    private Long propertyId;
    private String propertyType;
    private String fullName;
    private String fatherName;
    private String dateOfBirth;
    private String customerID;        //may be IC/passport Number.
    private String customerIDType;    //IC,Passport etc.

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

    // Account / Membership Info
    private String accountStatus; // "Active", "Inactive", "Suspended"
    private String registrationDate;

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

    // Rental Details
    private Double rentalAmount;
    private Double advanceRentalDeposit;
    private Double utilityDeposit;
    private Double stampingFee;
    private Double totalAmountForTenancy;
    private Integer rentalDurationInMonths; // e.g., 12 for 1 year
    private String rentalStartDate;
    private String rentalDueDate;
    private String contractStartDate;
    private String contractEndDate;
    private int gracePeriodInDays;

    // Buy Details
    private Double propertyPrice;
    private Double stampDutyFee;
    private Double registrationFee;
    private Double downPaymentAmount;
    private Double monthlyInstallmentAmount;
    private Integer numberOfInstallments;
    private String purchaseDate;
    private Double totalAmountPaid;

    // Computed d
    public Double getTotalAmountForTenancy() {
        double total = 0;
        if (rentalAmount != null) total += rentalAmount;
        if (advanceRentalDeposit != null) total += advanceRentalDeposit;
        if (utilityDeposit != null) total += utilityDeposit;
        if (stampingFee != null) total += stampingFee;
        return total;
    }
}

