package com.rsbusinesstech.rsbusinesstech_backend.roomrentalkl.areas.service;

import com.rsbusinesstech.rsbusinesstech_backend.roomrentalkl.areas.dto.AreasResponseDTO;
import com.rsbusinesstech.rsbusinesstech_backend.utils.JsonFileUtil;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreasService {

    @Autowired
    JsonFileUtil jsonFileUtil;

    public AreasResponseDTO getAreaByName(String areaName){
        if (StringUtils.isEmpty(areaName)) {
            throw new IllegalArgumentException("Area Name is required");
        }
        return jsonFileUtil.getAreaByName(areaName);
    }
}
