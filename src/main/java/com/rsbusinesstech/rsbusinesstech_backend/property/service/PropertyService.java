package com.rsbusinesstech.rsbusinesstech_backend.property.service;

import com.rsbusinesstech.rsbusinesstech_backend.property.dto.PropertyDTO;
import com.rsbusinesstech.rsbusinesstech_backend.property.utils.JsonFileUtil;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
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
    public PropertyDTO addPropertyByType(String type, PropertyDTO property){

        List<PropertyDTO> properties = JsonFileUtil.readPropertiesByType(type);
        long nextId = properties.stream().mapToLong(PropertyDTO::getId).max().orElse(0)+1;

        property.setId(nextId);

        properties.add(property);

        JsonFileUtil.writePropertiesByType(type,properties);
        return property;
    }

    //This method will update a Property by it's Type.
    public PropertyDTO updatePropertyByType(String type, PropertyDTO updatedProperty, Long id){
        List<PropertyDTO> properties = JsonFileUtil.readPropertiesByType(type);
        for(int i = 0; i < properties.size() ; i++){
            if(properties.get(i).getId().equals(id)){
                updatedProperty.setId(id);
                properties.set(i,updatedProperty);
                JsonFileUtil.writePropertiesByType(type,properties);
              return updatedProperty;
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
}
