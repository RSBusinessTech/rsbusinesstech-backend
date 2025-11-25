package com.rsbusinesstech.rsbusinesstech_backend.property.service;

import com.rsbusinesstech.rsbusinesstech_backend.property.dto.PropertyDTO;
import com.rsbusinesstech.rsbusinesstech_backend.property.utils.JsonFileUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PropertyService
{
    //This method will give all the properties for a particular Type.
    public List<PropertyDTO> getPropertiesByType(String type){
        return JsonFileUtil.readPropertiesByType(type);
    }


    //This method will add a Property by it's Type.
    public String addPropertyByType(String type, PropertyDTO property){

        List<PropertyDTO> properties = JsonFileUtil.readPropertiesByType(type);
        long nextId = properties.stream().mapToLong(PropertyDTO::getId).max().orElse(0)+1;

        property.setId(nextId);

        properties.add(property);

        JsonFileUtil.writePropertiesByType(type,properties);
        return "Property Added Successfully";
    }

    //This method will update a Property by it's Type.
    public String updatePropertyByType(String type, PropertyDTO updatedProperty, Long id){
        List<PropertyDTO> properties = JsonFileUtil.readPropertiesByType(type);
        for(int i = 0; i < properties.size() ; i++){
            if(properties.get(i).getId().equals(id)){
                updatedProperty.setId(id);
                properties.set(i,updatedProperty);
                JsonFileUtil.writePropertiesByType(type,properties);
              return "Property Updated Successfully";
            }
        }
        throw new RuntimeException("Property not found");
    }

    //This method will delete a Property by it's Type & Id.
    public boolean deletePropertyByType(String type, Long id){
        List<PropertyDTO> properties = JsonFileUtil.readPropertiesByType(type);
        boolean removed = properties.removeIf(property -> property.getId().equals(id));
        if (removed) {
            JsonFileUtil.writePropertiesByType(type,properties);
        }
        return removed;
    }
}
