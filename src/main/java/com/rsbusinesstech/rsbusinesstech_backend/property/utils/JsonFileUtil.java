package com.rsbusinesstech.rsbusinesstech_backend.property.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsbusinesstech.rsbusinesstech_backend.property.dto.PropertyDTO;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonFileUtil {

    private static final String EXTERNAL_BASE_PATH = "/opt/app/data/"; // server path.
    private static final String LOCAL_BASE_PATH = "data/";              // packaged defaults.

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Read JSON file.
    public static List<PropertyDTO> readPropertiesByType(String type) {
        String externalFilePath = EXTERNAL_BASE_PATH + type.toLowerCase() + ".json";
        File externalFile = new File(externalFilePath);

        TypeReference<List<PropertyDTO>> typeReference = new TypeReference<>() {};

        try {
            if (externalFile.exists()) {
                // Read from external file if it exists.
                return objectMapper.readValue(externalFile, typeReference);
            } else {
                // Fallback: read from classpath resource.
                ClassPathResource resource = new ClassPathResource(LOCAL_BASE_PATH + type.toLowerCase() + ".json");
                return objectMapper.readValue(resource.getInputStream(), typeReference);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON: " + externalFilePath, e);
        }
    }

    // Write JSON file.
    public static void writePropertiesByType(String type, List<PropertyDTO> properties) {
        String externalFilePath = EXTERNAL_BASE_PATH + type.toLowerCase() + ".json";
        File externalFile = new File(externalFilePath);

        // Ensure the parent directory exists (/opt/app/data/)
        externalFile.getParentFile().mkdirs();

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(externalFile, properties);
        } catch (IOException e) {
            throw new RuntimeException("Error writing JSON: " + externalFilePath, e);
        }
    }
}
