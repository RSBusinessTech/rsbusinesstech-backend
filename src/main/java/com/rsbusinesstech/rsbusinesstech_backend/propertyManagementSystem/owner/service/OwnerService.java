package com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.owner.service;

import com.rsbusinesstech.rsbusinesstech_backend.commonUtils.CloudinaryService;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.owner.dto.OwnerDTO;
import com.rsbusinesstech.rsbusinesstech_backend.utils.JsonFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OwnerService
{
    @Autowired
    JsonFileUtil jsonFileUtil;

    @Autowired
    CloudinaryService cloudinaryService;

    //This method will give all the owners for a particular agent.
    public List<OwnerDTO> getAllOwners(String agentId){
        if(agentId == null || agentId.length() == 0){
            throw new IllegalArgumentException("AgentId is required");
        }
        List<OwnerDTO> allOwners = Optional.ofNullable(jsonFileUtil.readOwners()).orElse(Collections.emptyList());
        List<OwnerDTO> agentCustomers = allOwners.stream().filter(ownerDTO -> agentId.equalsIgnoreCase(ownerDTO.getAgentId())).collect(Collectors.toList());
        return agentCustomers;
    }

    //This method will give all the owners for a particular agent & propertyType.
    public List<OwnerDTO> getAllOwnersByPropertyType(String agentId, String propertyType){
        if(agentId == null || agentId.length() == 0){
            throw new IllegalArgumentException("AgentId is required");
        }
        if(propertyType == null || propertyType.length() == 0){
            throw new IllegalArgumentException("Property Type is required");
        }
        List<OwnerDTO> allOwners = Optional.ofNullable(jsonFileUtil.readOwners()).orElse(Collections.emptyList());
        List<OwnerDTO> agentOwners = allOwners.stream().filter(customerDTO -> agentId.equalsIgnoreCase(customerDTO.getAgentId()) && propertyType.equalsIgnoreCase(customerDTO.getPropertyType())).collect(Collectors.toList());
        return agentOwners;
    }


    //This method will add an Owner by it's Type.
    public OwnerDTO addOwner(OwnerDTO Owner){

        if(Owner == null){
           throw new IllegalArgumentException("Owner cann't be null");
        }
        if(Owner.getAgentId() == null || Owner.getAgentId().length() == 0){
            throw new IllegalArgumentException("AgentId is required");
        }

        List<OwnerDTO> owners = jsonFileUtil.readOwners();
        long nextId = owners.stream().mapToLong(OwnerDTO::getId).max().orElse(0)+1;
        Owner.setId(nextId);
        Owner.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kuala_Lumpur")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a")));  //a → AM/PM marker.
        Owner.setCreatedBy(Owner.getAgentId());

        owners.add(Owner);

        jsonFileUtil.writeOwners(owners);
        return Owner;
    }

    //This method will update an Owner by it's Type.
    public OwnerDTO updateOwner(OwnerDTO updatedOwner, Long id){
        List<OwnerDTO> owners = jsonFileUtil.readOwners();

        for(int i = 0; i < owners.size() ; i++){
            if(owners.get(i).getId().equals(id)){
                updatedOwner.setId(id);
                updatedOwner.setUpdatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kuala_Lumpur")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a"))); //a → AM/PM marker.
                updatedOwner.setUpdatedBy(updatedOwner.getAgentId());

                // Only delete and replace images if new images are provided.
                if(updatedOwner.getImageUrl() != null && !updatedOwner.getImageUrl().isEmpty()) {
                    if(owners.get(i).getImagePublicId() != null && !owners.get(i).getImagePublicId().isEmpty()) {
                        cloudinaryService.deleteFile(owners.get(i).getImagePublicId());
                    }
                } else {
                    // Keep existing images if no new images are uploaded.
                    updatedOwner.setImageUrl(owners.get(i).getImageUrl());
                    updatedOwner.setImagePublicId(owners.get(i).getImagePublicId());
                }
                owners.set(i,updatedOwner);
                jsonFileUtil.writeOwners(owners);
              return updatedOwner;
            }
        }
        throw new RuntimeException("Owner not found");
    }

    // This method will delete an Owner by its Type & Id, including Cloudinary images.
    public boolean deleteOwner(Long id) {
        List<OwnerDTO> owners = jsonFileUtil.readOwners();

        // Find the owner to delete.
        OwnerDTO ownerToDelete = owners.stream()
                                   .filter(owner -> owner.getId().equals(id))
                                   .findFirst()
                                   .orElse(null);

        if (ownerToDelete != null) {
            // 1. Delete existing images from Cloudinary.
            if (ownerToDelete.getImagePublicId() != null && !ownerToDelete.getImagePublicId().isEmpty()) {
                cloudinaryService.deleteFile(ownerToDelete.getImagePublicId());
            }

            // 2. Remove owner from list.
            owners.remove(ownerToDelete);

            // 3. Write updated list back to JSON.
            jsonFileUtil.writeOwners(owners);
            return true;
        }
        return false; // Owner not found.
    }

    public Map<String,Long> getOwnersInfo(String agentId){
        Map<String,Long> ownersInfoMap = new HashMap<>();
        List<OwnerDTO> owners = jsonFileUtil.readOwners();

        long totalOwners = owners.stream()
                                 .filter(ownerDTO -> agentId.equalsIgnoreCase(ownerDTO.getAgentId()))
                                 .count();

        ownersInfoMap.put("totalOwners",totalOwners);
        return ownersInfoMap;
    }

}
