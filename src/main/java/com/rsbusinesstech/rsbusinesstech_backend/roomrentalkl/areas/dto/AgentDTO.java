package com.rsbusinesstech.rsbusinesstech_backend.roomrentalkl.areas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AgentDTO {

    private String name;
    private String phone;
    private String whatsapp;
    private String email;
    private String avatar;
    private Boolean verified;
    private String agencyName;
    private String renTag;
}