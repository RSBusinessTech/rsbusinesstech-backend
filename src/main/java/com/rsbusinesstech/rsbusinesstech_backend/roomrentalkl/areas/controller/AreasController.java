package com.rsbusinesstech.rsbusinesstech_backend.roomrentalkl.areas.controller;

import com.rsbusinesstech.rsbusinesstech_backend.roomrentalkl.areas.dto.AreasResponseDTO;
import com.rsbusinesstech.rsbusinesstech_backend.roomrentalkl.areas.service.AreasService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200", "https://rsbusinesstech.com","https://roomrentalkl.com"})
@RequestMapping("/roomrentalkl/areas")
@RestController
public class AreasController {

    @Autowired
    AreasService areasService;

    @GetMapping("/{areaName}")
    public ResponseEntity<AreasResponseDTO> getArea(@PathVariable String areaName){
        AreasResponseDTO areasResponseDTO = null;
        try{
            if(!StringUtils.isEmpty(areaName)){
                areasResponseDTO = areasService.getAreaByName(areaName);
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(areasResponseDTO);
        }
        return ResponseEntity.ok(areasResponseDTO);
    }
}
