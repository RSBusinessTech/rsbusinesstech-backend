package com.rsbusinesstech.rsbusinesstech_backend.roomrentalkl.areas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NearbyAreaDTO {

    private String name;

    private String slug;

    private Integer averageRent;

    // MRT / LRT accessibility
    private Boolean mrtAccess;

    // Distance from current area in KM
    private Integer distanceFromArea;

    // SEO-friendly description of who this area is suitable for
    private String popularFor;

    //Description
    private String description;
}