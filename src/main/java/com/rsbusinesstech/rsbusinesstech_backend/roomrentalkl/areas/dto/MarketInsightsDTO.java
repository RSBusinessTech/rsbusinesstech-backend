package com.rsbusinesstech.rsbusinesstech_backend.roomrentalkl.areas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarketInsightsDTO {
    private Integer averageRent;
    private Double priceGrowth;
    private String demandLevel;
    private Double vacancyRate;
}
