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
public class TransitStationDTO {

    // =========================
    // BASIC INFO
    // =========================
    private String name;
    private String slug;

    // MRT / LRT / MONORAIL / KTM / BRT
    private String stationType;

    private String line;

    // =========================
    // DISTANCE INFO
    // =========================
    private String walkingTime;
    private Integer distanceMeters;
    private String distanceText;

    // =========================
    // CONTENT (SEO PAGE)
    // =========================
    private String title;
    private String shortDescription;

    // =========================
    // RENT DATA
    // =========================
    private Integer averageRoomRent;

    // =========================
    // SEO FIELDS
    // =========================
    private String seoTitle;
    private String seoDescription;
    private String canonicalSlug;

    // =========================
    // FAQ (SEO BOOST)
    // =========================
    private String faqQuestion;
    private String faqAnswer;

    // =========================
    // NEARBY DATA
    // =========================
    private List<String> nearbyLandmarks;
    private List<String> nearbyResidences;

    // =========================
    // SEO KEYWORDS
    // =========================
    private List<String> popularSearches;

    // =========================
    // OPTIONAL (FUTURE EXPANSION)
    // =========================
    private List<String> nearbyTransitTypes;
}