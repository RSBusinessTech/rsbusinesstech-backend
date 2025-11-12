package com.rsbusinesstech.rsbusinesstech_backend.property.controller;

import com.rsbusinesstech.rsbusinesstech_backend.contact.model.EmailRequest;
import com.rsbusinesstech.rsbusinesstech_backend.property.dto.PropertyDTO;
import com.rsbusinesstech.rsbusinesstech_backend.property.service.PropertyService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "https://rsbusinesstech.com","https://vyenpropertyadvisor.com"})
@RestController
@RequestMapping("/property")
public class PropertyController
{

    @Autowired
    PropertyService propertyService;

    @GetMapping("/getPropertyByType")
    public ResponseEntity<List<PropertyDTO>> getPropertyByType(@RequestParam String type){
        List<PropertyDTO> properties = new ArrayList<>();
        try{
            if(!StringUtils.isEmpty(type)){
                properties = propertyService.getPropertiesByType(type);
            }
        }catch (Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
        }
        return ResponseEntity.ok(properties);
    }

}
