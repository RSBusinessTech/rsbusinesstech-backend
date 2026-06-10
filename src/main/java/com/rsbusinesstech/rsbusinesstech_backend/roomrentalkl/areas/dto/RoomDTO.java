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
public class RoomDTO {

    // =========================
    // BASIC INFO
    // =========================
    private Long id;
    private String title;
    private String slug;
    private String description;

    // =========================
    // IMAGES
    // =========================
    private List<String> images;
    private String thumbnail;
    private Integer currentImageIndex;

    // =========================
    // PRICE INFO
    // =========================
    private Integer price;
    private Double pricePerSqft;
    private Integer deposit;
    private Integer maintenanceFee;

    // =========================
    // ROOM / UNIT SPECS
    // =========================
    private String roomType;
    private String propertyType;

    private Integer bedrooms;
    private Integer bathrooms;
    private Integer carparks;
    private Integer sqft;

    private String furnished;

    // =========================
    // LOCATION INFO
    // =========================
    private String address;
    private String area;
    private String city;
    private String state;
    private String postalCode;

    // =========================
    // TRANSPORT
    // =========================
    private String distanceToMrt;
    private String mrtStationName;

    private String distanceToLrt;
    private String lrtStationName;

    // =========================
    // LISTING INFO
    // =========================
    private String listedDate;
    private String updatedDate;

    private Boolean isNew;
    private Boolean featured;

    // =========================
    // AGENT
    // =========================
    private AgentDTO agent;

    // =========================
    // SOCIAL / ENGAGEMENT
    // =========================
    private Integer views;
    private Integer likes;
    private Boolean isLiked;

    // =========================
    // TAGS
    // =========================
    private List<String> tags;

    // =========================
    // MEDIA EXTRA
    // =========================
    private String videoTourUrl;
    private String floorPlanUrl;
}