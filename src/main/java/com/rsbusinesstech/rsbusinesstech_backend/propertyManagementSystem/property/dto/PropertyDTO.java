package com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.property.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDTO {
    private Long id;
    private Long customerId;
    private String name;
    private double price;
    private String address;
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer carParks;
    private String furnishing;
    private double sizeSqft;
    private List<String> imageUrls;
    private List<String> imagePublicIds;  //to delete images from clouds.
    private List<String> amenities;
    private List<String> commonFacilities;
    private String location;
    private String videoURL;
    private String isActive = "YES";  //Yes = show in UI, No = Don't show in UI.

    // System Metadata
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
}
