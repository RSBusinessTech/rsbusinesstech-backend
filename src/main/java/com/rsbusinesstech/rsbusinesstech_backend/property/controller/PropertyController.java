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
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to Fetch Properties");
        }
        return ResponseEntity.ok(properties);
    }

    @PostMapping("/addPropertyByType")
    public ResponseEntity<String> addPropertyByType(@RequestParam String type, @RequestBody PropertyDTO property){
        String response = "Something went wrong";
        try{
            if(!StringUtils.isEmpty(type) && property != null){
                String videoURL = property.getVideoURL();
                if(!StringUtils.isEmpty(videoURL) && videoURL.contains("watch?v=")){
                    videoURL = videoURL.replace("watch?v=","embed/");
                    property.setVideoURL(videoURL);
                }
                response = propertyService.addPropertyByType(type,property);
            }
        }catch (Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to Add Property");
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updatePropertyByType")
    public ResponseEntity<String> updatePropertyByType(@RequestParam String type, @RequestBody PropertyDTO property, @RequestParam String id){
        String response = "Something went wrong";
        try{
            if(!StringUtils.isEmpty(type) && property != null && id != null){
                String videoURL = property.getVideoURL();
                if(!StringUtils.isEmpty(videoURL) && videoURL.contains("watch?v=")){
                    videoURL = videoURL.replace("watch?v=","embed/");
                    property.setVideoURL(videoURL);
                }
                response = propertyService.updatePropertyByType(type,property,Long.parseLong(id));
            }
        }catch (Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to Update Property");
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deletePropertyByType")
    public ResponseEntity<String> deletePropertyByType(@RequestParam String type, @RequestParam String id){
        try{
            if(!StringUtils.isEmpty(type) && id != null){
                propertyService.deletePropertyByType(type,Long.parseLong(id));
            }
        }catch (Exception e){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to Delete Property");
        }
        return ResponseEntity.ok("Property Deleted Suuceesfully");
    }


}
