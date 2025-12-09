package com.rsbusinesstech.rsbusinesstech_backend.customer.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rsbusinesstech.rsbusinesstech_backend.customer.dto.CustomerDTO;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonFileUtil {

    private static final String EXTERNAL_BASE_PATH = "/opt/app/data/"; // server path.
    private static final String LOCAL_BASE_PATH = "opt/app.data/";              // packaged defaults.

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Read JSON file.
    public static List<CustomerDTO> readCustomers() {
        String externalFilePath = EXTERNAL_BASE_PATH + "customer.json";
        File externalFile = new File(externalFilePath);

        TypeReference<List<CustomerDTO>> typeReference = new TypeReference<>() {};

        try {
            if (externalFile.exists()) {
                // Read from external file if it exists.
                return objectMapper.readValue(externalFile, typeReference);
            } else {
                // Fallback: read from classpath resource.
                ClassPathResource resource = new ClassPathResource(LOCAL_BASE_PATH + "customer.json");
                return objectMapper.readValue(resource.getInputStream(), typeReference);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON: " + externalFilePath, e);
        }
    }

    // Write JSON file.
    public static void writeCustomers(List<CustomerDTO> customers) {
        String externalFilePath = EXTERNAL_BASE_PATH + "customer.json";
        File externalFile = new File(externalFilePath);

        // Ensure the parent directory exists (/opt/app/data/)
        externalFile.getParentFile().mkdirs();

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(externalFile, customers);
        } catch (IOException e) {
            throw new RuntimeException("Error writing JSON: " + externalFilePath, e);
        }
    }
}
