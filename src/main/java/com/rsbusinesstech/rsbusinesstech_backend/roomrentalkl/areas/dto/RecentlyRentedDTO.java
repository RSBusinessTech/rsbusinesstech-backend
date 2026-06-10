package com.rsbusinesstech.rsbusinesstech_backend.roomrentalkl.areas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecentlyRentedDTO {
    private String title;
    private Integer price;
    private String rentedDate;
}
