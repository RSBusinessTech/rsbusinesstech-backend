package com.rsbusinesstech.rsbusinesstech_backend.roomrentalkl.areas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AreaDTO {
    private String name;
    private String slug;
    private String heroImage;
    private String metaTitle;
    private String metaDescription;
    private Integer averageRent;
    private Integer totalListings;
}
