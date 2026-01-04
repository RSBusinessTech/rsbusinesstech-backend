package com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.owner.controller;

import com.rsbusinesstech.rsbusinesstech_backend.commonUtils.CloudinaryService;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.owner.dto.OwnerDTO;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.owner.service.OwnerService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:4200", "https://rsbusinesstech.com"})
@RestController
@RequestMapping("/owner")
public class OwnerController {
    @Autowired
    OwnerService ownerService;

    @Autowired
    CloudinaryService cloudinaryService;

    String mediaFolder = "Vyen_Property_Advisor";
    String backupFolder = "Vyen_Property_Advisor/Backup";

    @GetMapping("/getAllOwners/{agentId}")
    public ResponseEntity<List<OwnerDTO>> getAllOwners(@PathVariable String agentId){
        List<OwnerDTO> owners = new ArrayList<>();
        try{
            owners = ownerService.getAllOwners(agentId);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(owners);
        }
        return ResponseEntity.ok(owners);
    }

    @GetMapping("/getAllOwners/{agentId}/{propertyType}")
    public ResponseEntity<List<OwnerDTO>> getAllOwnersByPropertyType(@PathVariable String agentId, @PathVariable String propertyType){
        List<OwnerDTO> owners = new ArrayList<>();
        try{
            owners = ownerService.getAllOwnersByPropertyType(agentId, propertyType);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(owners);
        }
        return ResponseEntity.ok(owners);
    }

    @GetMapping("/getOwnerById/{id}/{agentId}")
    public ResponseEntity<OwnerDTO> getOwnerById(@PathVariable String id, @PathVariable String agentId){
        List<OwnerDTO> owners = new ArrayList<>();
        OwnerDTO owner = null;
        try{
            owners = ownerService.getAllOwners(agentId);
            Optional<OwnerDTO> ownerDTOOptional = owners.stream().filter(ownerDTO -> String.valueOf(ownerDTO.getId()).equalsIgnoreCase(id)).findFirst();
            if(ownerDTOOptional.isPresent()){
                owner =  ownerDTOOptional.get();
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(owner);
        }
        return ResponseEntity.ok(owner);
    }

    @PostMapping("/addOwner")
    public ResponseEntity<OwnerDTO> addOwner(@RequestPart("owner") OwnerDTO owner,
                                                   @RequestPart (value = "images", required = false) List<MultipartFile> images){
        OwnerDTO newOwner = null;
        try{
            // 1. Upload images to cloudinary.
            List<String> urls = new ArrayList<>();
            List<String> publicIDs = new ArrayList<>();
            if (images != null && images.size() > 0) {
                Map<String,List<String>> responseMap  = cloudinaryService.uploadFiles(images, mediaFolder); // your cloud upload logic.
                urls = responseMap.get("urls");
                publicIDs = responseMap.get("publicIDs");
                // 2. Set URLs to property
                owner.setImageUrl(urls.get(0));
                owner.setImagePublicId(publicIDs.get(0));
            }
            newOwner = ownerService.addOwner(owner);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(newOwner);
        }
        return ResponseEntity.ok(newOwner);
    }

    @PutMapping("/updateOwner")
    public ResponseEntity<OwnerDTO> updateOwner(@RequestPart("owner") OwnerDTO owner, @RequestParam String id,
                                                      @RequestPart (value = "images", required = false) List<MultipartFile> images ){
        OwnerDTO updatedOwner = null;
        try{
            if(owner != null && !StringUtils.isEmpty(id)){
                // 1. Upload images to cloudinary.
                List<String> urls = new ArrayList<>();
                List<String> publicIDs = new ArrayList<>();
                if (images != null && images.size() > 0) {
                    Map<String,List<String>> responseMap = cloudinaryService.uploadFiles(images, mediaFolder); // your cloud upload logic.
                    urls = responseMap.get("urls");
                    publicIDs = responseMap.get("publicIDs");

                    // 2. Set URLs to property
                    owner.setImageUrl(urls.get(0));
                    owner.setImagePublicId(publicIDs.get(0));
                }

                updatedOwner = ownerService.updateOwner(owner, Long.parseLong(id));
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(updatedOwner);
        }
        return ResponseEntity.ok(updatedOwner);
    }

    @DeleteMapping("/deleteOwner")
    public ResponseEntity<String> deleteOwner(@RequestParam String id){
        try{
            if(!StringUtils.isEmpty(id)){
                ownerService.deleteOwner( Long.parseLong(id));
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to Delete Customer");
        }
        return ResponseEntity.ok("Owner Deleted Successfully");
    }

}
