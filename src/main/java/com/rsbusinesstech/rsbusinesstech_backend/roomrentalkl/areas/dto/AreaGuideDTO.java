package com.rsbusinesstech.rsbusinesstech_backend.roomrentalkl.areas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AreaGuideDTO {

    private String title;

    private String description;

    private String buttonText;

    private String buttonLink;

    private List<String> highlights;

    private List<FeatureDTO> features;

    private List<String> popularResidences;

    private List<String> nearbyLandmarks;

    private List<String> nearbyUniversities;

    private List<String> majorEmployers;
}