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
public class InternalLinkingDTO {

    private List<String> relatedAreas;

    private List<String> nearbyHotspots;

    private List<String> relatedSearchPages;

    private List<String> internalPages;
}
