package com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.property.service;

import com.rsbusinesstech.rsbusinesstech_backend.commonUtils.CloudinaryService;
import com.rsbusinesstech.rsbusinesstech_backend.propertyManagementSystem.property.dto.PropertyDTO;
import com.rsbusinesstech.rsbusinesstech_backend.utils.JsonFileUtil;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
public class PropertyService
{
    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    JsonFileUtil jsonFileUtil;

    //This method will give all the properties for a particular Type.
    public List<PropertyDTO> getPropertiesByType(String type){
        return jsonFileUtil.readPropertiesByType(type);
    }


    //This method will add a Property by it's Type.
    public PropertyDTO addPropertyByType(String type, PropertyDTO property){

        List<PropertyDTO> properties = jsonFileUtil.readPropertiesByType(type);
        long nextId = properties.stream().mapToLong(PropertyDTO::getId).max().orElse(0)+1;
        property.setId(nextId);
        property.setCreatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kuala_Lumpur")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a")));  //a → AM/PM marker.
        property.setCreatedBy("admin");
        properties.add(property);

        jsonFileUtil.writePropertiesByType(type,properties);
        return property;
    }

    //This method will update a Property by it's Type.
    public PropertyDTO updatePropertyByType(String type, PropertyDTO updatedProperty, Long id){
        List<PropertyDTO> properties = jsonFileUtil.readPropertiesByType(type);

        for(int i = 0; i < properties.size() ; i++){
            if(properties.get(i).getId().equals(id)){
                updatedProperty.setId(id);
                updatedProperty.setUpdatedAt(ZonedDateTime.now(ZoneId.of("Asia/Kuala_Lumpur")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss a"))); //a → AM/PM marker.
                updatedProperty.setUpdatedBy("admin");

                // Only delete and replace images if new images are provided.
                if(updatedProperty.getImageUrls() != null && !updatedProperty.getImageUrls().isEmpty()) {
                    if(properties.get(i).getImagePublicIds() != null && !properties.get(i).getImagePublicIds().isEmpty()) {
                        cloudinaryService.deleteFiles(properties.get(i).getImagePublicIds());
                    }
                } else {
                    // Keep existing images if no new images are uploaded.
                    updatedProperty.setImageUrls(properties.get(i).getImageUrls());
                    updatedProperty.setImagePublicIds(properties.get(i).getImagePublicIds());
                }

                properties.set(i,updatedProperty);
                jsonFileUtil.writePropertiesByType(type,properties);
              return updatedProperty;
            }
        }
        throw new RuntimeException("Property not found");
    }

    // This method will delete a Property by its Type & Id, including Cloudinary images.
    public boolean deletePropertyByType(String type, Long id) {
        List<PropertyDTO> properties = jsonFileUtil.readPropertiesByType(type);

        // Find the property to delete
        PropertyDTO propertyToDelete = properties.stream()
                .filter(property -> property.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (propertyToDelete != null) {
            // 1. Delete existing images from Cloudinary.
            if (propertyToDelete.getImagePublicIds() != null && !propertyToDelete.getImagePublicIds().isEmpty()) {
                cloudinaryService.deleteFiles(propertyToDelete.getImagePublicIds());
            }

            // 2. Remove property from list.
            properties.remove(propertyToDelete);

            // 3. Write updated list back to JSON.
            jsonFileUtil.writePropertiesByType(type, properties);
            return true;
        }

        return false; // Property not found.
    }


    //This method will handle the YouTube video link.
    public String convertToEmbedURL(String videoURL) {
        if (StringUtils.isEmpty(videoURL)) return "";

        try {
            URI uri = new URI(videoURL);
            String host = uri.getHost();
            String query = uri.getQuery();
            String path = uri.getPath();

            // If it's already an embed URL, return as is.
            if (videoURL.contains("/embed/")) return videoURL;

            // If it's a standard watch URL
            if (videoURL.contains("watch")) {
                String videoId = null;
                String list = null;
                String index = null;

                if (query != null) {
                    String[] params = query.split("&");
                    for (String param : params) {
                        if (param.startsWith("v=")) videoId = param.substring(2);
                        if (param.startsWith("list=")) list = param.substring(5);
                        if (param.startsWith("index=")) index = param.substring(6);
                    }
                }

                if (videoId != null) {
                    String embedURL = "https://www.youtube.com/embed/" + videoId;
                    StringBuilder sb = new StringBuilder(embedURL);
                    boolean hasParam = false;
                    if (list != null) {
                        sb.append("?list=").append(list);
                        hasParam = true;
                    }
                    if (index != null) {
                        sb.append(hasParam ? "&" : "?").append("index=").append(index);
                    }
                    return sb.toString();
                }
            }

            // Fallback: return original.
            return videoURL;

        } catch (URISyntaxException e) {
            return videoURL;
        }
    }

    public long getAllPropertiesCount(){
        long count = 0;
        List<String> allProperties = Arrays.asList("buy","commercial","mm2h","newprojects","rent");
        for(String type : allProperties){
            long propertiesCount = jsonFileUtil.countPropertiesByType(type);
            count = count + propertiesCount;
        }
        return count;
    }

    public long getPropertiesCountByType(String type){
        return jsonFileUtil.countPropertiesByType(type);
    }
}
