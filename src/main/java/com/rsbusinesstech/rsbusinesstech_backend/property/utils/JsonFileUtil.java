package com.rsbusinesstech.rsbusinesstech_backend.property.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsbusinesstech.rsbusinesstech_backend.property.dto.PropertyDTO;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonFileUtil {

    private static final String BASE_PATH = "src/main/resources/data/";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    //read JSON file.
    public static List<PropertyDTO> readPropertiesByType(String type){
        String filePath = BASE_PATH + type.toLowerCase()+".json";
        File file = new File(filePath);
        TypeReference<List<PropertyDTO>> typeReference = new TypeReference <List<PropertyDTO>>() {};
        try {
            return objectMapper.readValue(file, typeReference);
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON: " + filePath, e);
        }
    }

    //write into JSON file.
    public static void writePropertiesByType(String type, List<PropertyDTO> properties){
        String filePath = BASE_PATH + type.toLowerCase()+".json";
        File file = new File(filePath);
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, properties);
        } catch (IOException e) {
            throw new RuntimeException("Error writing JSON: " + filePath, e);
        }
    }
}
