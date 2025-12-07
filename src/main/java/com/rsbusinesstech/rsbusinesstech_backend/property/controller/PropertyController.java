package com.rsbusinesstech.rsbusinesstech_backend.property.controller;

import com.rsbusinesstech.rsbusinesstech_backend.property.dto.PropertyDTO;
import com.rsbusinesstech.rsbusinesstech_backend.commonUtils.CloudinaryService;
import com.rsbusinesstech.rsbusinesstech_backend.property.service.PropertyService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200", "https://rsbusinesstech.com","https://vyenpropertyadvisor.com"})
@RestController
@RequestMapping("/property")
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    @Autowired
    CloudinaryService cloudinaryService;

    String mediaFolder = "Vyen_Property_Advisor";

    @GetMapping("/getPropertyByType")
    public ResponseEntity<List<PropertyDTO>> getPropertyByType(@RequestParam String type){
        List<PropertyDTO> properties = new ArrayList<>();
        try{
            if(!StringUtils.isEmpty(type)){
                properties = propertyService.getPropertiesByType(type);
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(properties);
        }
        return ResponseEntity.ok(properties);
    }

    @PostMapping("/addPropertyByType")
    public ResponseEntity<PropertyDTO> addPropertyByType(@RequestParam String type, @RequestPart("property") PropertyDTO property,
                                                    @RequestPart (value = "images", required = false) List<MultipartFile> images){
        PropertyDTO newProperty = null;
        try{
            if(!StringUtils.isEmpty(type) && property != null){
                // 1. Upload images to cloudinary.
                List<String> urls = new ArrayList<>();
                List<String> publicIDs = new ArrayList<>();
                if (images != null && images.size() > 0) {
                    Map<String,List<String>> responseMap  = cloudinaryService.uploadFiles(images,mediaFolder); // your cloud upload logic.
                    urls = responseMap.get("urls");
                    publicIDs = responseMap.get("publicIDs");
                }
                // 2. Set URLs to property.
                property.setImageUrls(urls);
                property.setImagePublicIds(publicIDs);

                property.setVideoURL(propertyService.convertToEmbedURL(property.getVideoURL()));
                newProperty = propertyService.addPropertyByType(type, property);
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(newProperty);
        }
        return ResponseEntity.ok(newProperty);
    }

    @PutMapping("/updatePropertyByType")
    public ResponseEntity<PropertyDTO> updatePropertyByType(@RequestParam String type, @RequestPart("property") PropertyDTO property, @RequestParam String id,
                                                            @RequestPart (value = "images", required = false) List<MultipartFile> images ){
        PropertyDTO updatedProperty = null;
        try{
            if(!StringUtils.isEmpty(type) && property != null && id != null){
                // 1. Upload images to cloudinary.
                List<String> urls = new ArrayList<>();
                List<String> publicIDs = new ArrayList<>();
                if (images != null && images.size() > 0) {
                    Map<String,List<String>> responseMap = cloudinaryService.uploadFiles(images, mediaFolder); // your cloud upload logic.
                    urls = responseMap.get("urls");
                    publicIDs = responseMap.get("publicIDs");
                }
                // 2. Set URLs to property
                property.setImageUrls(urls);
                property.setImagePublicIds(publicIDs);

                property.setVideoURL(propertyService.convertToEmbedURL(property.getVideoURL()));
                updatedProperty = propertyService.updatePropertyByType(type, property, Long.parseLong(id));
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(updatedProperty);
        }
        return ResponseEntity.ok(updatedProperty);
    }

    @DeleteMapping("/deletePropertyByType")
    public ResponseEntity<String> deletePropertyByType(@RequestParam String type, @RequestParam String id){
        try{
            if(!StringUtils.isEmpty(type) && id != null){
                propertyService.deletePropertyByType(type, Long.parseLong(id));
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to Delete Property");
        }
        return ResponseEntity.ok("Property Deleted Successfully");
    }

}
