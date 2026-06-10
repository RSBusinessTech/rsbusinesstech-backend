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
public class AreasResponseDTO {
    private AreaDTO area;
    private List<RoomDTO> featuredRooms;
    private List<RoomDTO> latestRooms;
    private AreaGuideDTO areaGuide;
    private List<MrtStationDTO> mrtStations;
    private List<PriceGuideDTO> priceGuide;
    private MarketInsightsDTO marketInsights;
    private List<NearbyAreaDTO> nearbyAreas;
    private List<PopularSearchDTO> popularSearches;
    private List<RecentlyRentedDTO> recentlyRented;
    private List<RoomRequestDTO> roomRequests;
    private List<FaqDTO> faqs;
}
