package com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.email.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalPaymentReminderRequest
{
    /*
       @NotBlank: it check following things and works with String only.
                 i.   Value must not be null.
                 ii.  Value must not be empty.
                 iii. Value must contain at least one non-whitespace character.
    */

    // Basic Info
    @NotBlank
    private String customerName;
    @NotBlank
    private String propertyName;
    @NotBlank
    private String agentName;

    // Contact Info
    @NotBlank
    @Email
    private String customerEmail;
    @NotBlank
    @Email
    private String agentEmail;
    @NotBlank
    private String agentMobileNo;

    // Rental Info
    @NotNull
    private Double rentalAmount;
    @NotBlank
    private String rentalStartDate;
    @NotBlank
    private String rentalDueDate;

}
