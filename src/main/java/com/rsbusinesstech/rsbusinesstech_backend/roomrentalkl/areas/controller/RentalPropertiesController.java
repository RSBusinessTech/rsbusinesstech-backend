package com.rsbusinesstech.rsbusinesstech_backend.roomrentalkl.areas.controller;

import com.rsbusinesstech.rsbusinesstech_backend.roomrentalkl.areas.dto.AreasResponseDTO;
import com.rsbusinesstech.rsbusinesstech_backend.roomrentalkl.areas.dto.RentalPropertiesResponseDTO;
import com.rsbusinesstech.rsbusinesstech_backend.roomrentalkl.areas.service.AreasService;
import com.rsbusinesstech.rsbusinesstech_backend.roomrentalkl.areas.service.RentalPropertiesService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:4200", "https://rsbusinesstech.com","https://roomrentalkl.com", "https://roomrentalkl.netlify.app"})
@RequestMapping("/roomrentalkl/rooms")
@RestController
public class RentalPropertiesController {

    @Autowired
    RentalPropertiesService rentalPropertiesService;

    @GetMapping("/{slug}")
    public ResponseEntity<RentalPropertiesResponseDTO> getRentalProperties(@PathVariable String slug){
        RentalPropertiesResponseDTO rentalPropertiesResponseDTO = null;
        try{
            if(!StringUtils.isEmpty(slug)){
                rentalPropertiesResponseDTO = rentalPropertiesService.getAllRentalProperties();
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(rentalPropertiesResponseDTO);
        }
        return ResponseEntity.ok(rentalPropertiesResponseDTO);
    }
}
