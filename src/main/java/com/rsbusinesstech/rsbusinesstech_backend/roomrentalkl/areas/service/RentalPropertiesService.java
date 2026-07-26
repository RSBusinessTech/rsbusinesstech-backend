package com.rsbusinesstech.rsbusinesstech_backend.roomrentalkl.areas.service;

import com.rsbusinesstech.rsbusinesstech_backend.roomrentalkl.areas.dto.AreasResponseDTO;
import com.rsbusinesstech.rsbusinesstech_backend.roomrentalkl.areas.dto.RentalPropertiesResponseDTO;
import com.rsbusinesstech.rsbusinesstech_backend.utils.JsonFileUtil;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalPropertiesService {

    @Autowired
    JsonFileUtil jsonFileUtil;

    public RentalPropertiesResponseDTO getAllRentalProperties(){
        return jsonFileUtil.getAllRentalProperties();
    }
}
