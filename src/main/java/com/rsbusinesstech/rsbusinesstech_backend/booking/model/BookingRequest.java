package com.rsbusinesstech.rsbusinesstech_backend.booking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest
{
    private String name;
    private String email;
    private String phone;
    private String serviceId;
    private String stylist;
    private String date;
    private String time;
}
