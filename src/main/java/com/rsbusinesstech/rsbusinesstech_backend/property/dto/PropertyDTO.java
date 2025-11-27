package com.rsbusinesstech.rsbusinesstech_backend.property.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDTO {
    private Long id;
    private String name;
    private double price;
    private String address;
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer carParks;
    private String furnishing;
    private double sizeSqft;
    private List<String> imageUrls;
    private List<String> amenities;
    private List<String> commonFacilities;
    private String location;
    private String videoURL;
}
