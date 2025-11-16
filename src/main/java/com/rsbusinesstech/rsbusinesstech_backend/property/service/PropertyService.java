package com.rsbusinesstech.rsbusinesstech_backend.property.service;

import com.rsbusinesstech.rsbusinesstech_backend.property.dto.PropertyDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PropertyService
{
    public List<PropertyDTO> getPropertiesByType(String type){
        List<PropertyDTO> properties = new ArrayList<>();

        if(type.equalsIgnoreCase("rent")){
            List<String> amenities = Arrays.asList(
                    "Air conditioner",
                    "Balcony",
                    "Bed",
                    "Covered car parking",
                    "Refrigerator",
                    "Washing machine",
                    "Water heater",
                    "Wardrobe",
                    "Sofa set",
                    "Dining table",
                    "Microwave oven",
                    "TV unit",
                    "High-speed WiFi",
                    "24-hour security"
            );

            List<String> commonFacilities = new ArrayList<>(Arrays.asList(
                    "Gym",
                    "Balcony",
                    "Covered car parking",
                    "TV unit",
                    "High-speed WiFi",
                    "24-hour security"
            ));

            PropertyDTO propertyDTO = new PropertyDTO();
            propertyDTO.setId(1l);
            propertyDTO.setName("Axis Crown @ Axis Pandan");
            propertyDTO.setAddress("(Pandan Indah, Ampang)");
            propertyDTO.setPrice(2000l);
            propertyDTO.setBedrooms(2);
            propertyDTO.setBathrooms(2);
            propertyDTO.setCarParks(1);
            propertyDTO.setFurnishing("Fully Furnished");
            propertyDTO.setSizeSqft(750);
            propertyDTO.setAmenities(amenities);
            propertyDTO.setCommonFacilities(commonFacilities);
            propertyDTO.setLocation("https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d15930.970407806825!2d101.68405868809338!3d3.1390036499999967!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31cc37d111111111%3A0x7a0b1d362bc3a0!2sKuala%20Lumpur%2C%20Federal%20Territory%20of%20Kuala%20Lumpur%2C%20Malaysia!5e0!3m2!1sen!2smy!4v1661430783654!5m2!1sen!2smy");

            PropertyDTO propertyDTO2 = new PropertyDTO();
            propertyDTO2.setId(2l);
            propertyDTO2.setName("Axis Crown @ Axis Pandan");
            propertyDTO2.setAddress("(Pandan Indah, Ampang)");
            propertyDTO2.setPrice(2500l);
            propertyDTO2.setBedrooms(2);
            propertyDTO2.setBathrooms(2);
            propertyDTO2.setCarParks(2);
            propertyDTO2.setFurnishing("Fully Furnished");
            propertyDTO2.setSizeSqft(750);
            propertyDTO2.setAmenities(amenities);
            propertyDTO2.setCommonFacilities(commonFacilities);
            propertyDTO2.setLocation("https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d15930.970407806825!2d101.68405868809338!3d3.1390036499999967!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31cc37d111111111%3A0x7a0b1d362bc3a0!2sKuala%20Lumpur%2C%20Federal%20Territory%20of%20Kuala%20Lumpur%2C%20Malaysia!5e0!3m2!1sen!2smy!4v1661430783654!5m2!1sen!2smy");

            PropertyDTO propertyDTO3 = new PropertyDTO();
            propertyDTO3.setId(3l);
            propertyDTO3.setName("Axis Crown @ Axis Pandan");
            propertyDTO3.setAddress("(Pandan Indah, Ampang)");
            propertyDTO3.setPrice(3000l);
            propertyDTO3.setBedrooms(2);
            propertyDTO3.setBathrooms(3);
            propertyDTO3.setCarParks(2);
            propertyDTO3.setFurnishing("Fully Furnished");
            propertyDTO3.setSizeSqft(750);
            propertyDTO3.setAmenities(amenities);
            propertyDTO3.setCommonFacilities(commonFacilities);
            propertyDTO3.setLocation("https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d15930.970407806825!2d101.68405868809338!3d3.1390036499999967!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31cc37d111111111%3A0x7a0b1d362bc3a0!2sKuala%20Lumpur%2C%20Federal%20Territory%20of%20Kuala%20Lumpur%2C%20Malaysia!5e0!3m2!1sen!2smy!4v1661430783654!5m2!1sen!2smy");

            properties.add(propertyDTO);
            properties.add(propertyDTO2);
            properties.add(propertyDTO3);

        }else if(type.equalsIgnoreCase("buy")){
            List<String> amenities = Arrays.asList(
                    "Air conditioner",
                    "Balcony",
                    "Bed",
                    "Covered car parking",
                    "Refrigerator",
                    "Washing machine",
                    "Water heater",
                    "Wardrobe",
                    "Sofa set",
                    "Dining table",
                    "Microwave oven",
                    "TV unit",
                    "High-speed WiFi",
                    "24-hour security"
            );

            List<String> commonFacilities = new ArrayList<>(Arrays.asList(
                    "Gym",
                    "Balcony",
                    "Covered car parking",
                    "TV unit",
                    "High-speed WiFi",
                    "24-hour security"
            ));

            PropertyDTO propertyDTO = new PropertyDTO();
            propertyDTO.setId(1l);
            propertyDTO.setName("Axis Crown @ Axis Pandan");
            propertyDTO.setAddress("(Pandan Indah, Ampang)");
            propertyDTO.setPrice(500000l);
            propertyDTO.setBedrooms(2);
            propertyDTO.setBathrooms(2);
            propertyDTO.setCarParks(1);
            propertyDTO.setFurnishing("Fully Furnished");
            propertyDTO.setSizeSqft(750);
            propertyDTO.setAmenities(amenities);
            propertyDTO.setCommonFacilities(commonFacilities);
            propertyDTO.setLocation("https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d15930.970407806825!2d101.68405868809338!3d3.1390036499999967!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31cc37d111111111%3A0x7a0b1d362bc3a0!2sKuala%20Lumpur%2C%20Federal%20Territory%20of%20Kuala%20Lumpur%2C%20Malaysia!5e0!3m2!1sen!2smy!4v1661430783654!5m2!1sen!2smy");

            properties.add(propertyDTO);
        }
        else if(type.equalsIgnoreCase("commercial")){
            List<String> amenities = Arrays.asList(
                    "Air conditioner",
                    "Balcony",
                    "Bed",
                    "Covered car parking",
                    "Refrigerator",
                    "Washing machine",
                    "Water heater",
                    "Wardrobe",
                    "Sofa set",
                    "Dining table",
                    "Microwave oven",
                    "TV unit",
                    "High-speed WiFi",
                    "24-hour security"
            );

            List<String> commonFacilities = new ArrayList<>(Arrays.asList(
                    "Gym",
                    "Balcony",
                    "Covered car parking",
                    "TV unit",
                    "High-speed WiFi",
                    "24-hour security"
            ));

            PropertyDTO propertyDTO = new PropertyDTO();
            propertyDTO.setId(1l);
            propertyDTO.setName("Axis Crown @ Axis Pandan");
            propertyDTO.setAddress("(Pandan Indah, Ampang)");
            propertyDTO.setPrice(800000l);
            propertyDTO.setBedrooms(2);
            propertyDTO.setBathrooms(2);
            propertyDTO.setCarParks(1);
            propertyDTO.setFurnishing("Fully Furnished");
            propertyDTO.setSizeSqft(750);
            propertyDTO.setAmenities(amenities);
            propertyDTO.setCommonFacilities(commonFacilities);
            propertyDTO.setLocation("https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d15930.970407806825!2d101.68405868809338!3d3.1390036499999967!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31cc37d111111111%3A0x7a0b1d362bc3a0!2sKuala%20Lumpur%2C%20Federal%20Territory%20of%20Kuala%20Lumpur%2C%20Malaysia!5e0!3m2!1sen!2smy!4v1661430783654!5m2!1sen!2smy");

            properties.add(propertyDTO);
        }
        else if(type.equalsIgnoreCase("mm2h")){
            List<String> amenities = Arrays.asList(
                    "Air conditioner",
                    "Balcony",
                    "Bed",
                    "Covered car parking",
                    "Refrigerator",
                    "Washing machine",
                    "Water heater",
                    "Wardrobe",
                    "Sofa set",
                    "Dining table",
                    "Microwave oven",
                    "TV unit",
                    "High-speed WiFi",
                    "24-hour security"
            );

            List<String> commonFacilities = new ArrayList<>(Arrays.asList(
                    "Gym",
                    "Balcony",
                    "Covered car parking",
                    "TV unit",
                    "High-speed WiFi",
                    "24-hour security"
            ));

            PropertyDTO propertyDTO = new PropertyDTO();
            propertyDTO.setId(1l);
            propertyDTO.setName("Axis Crown @ Axis Pandan");
            propertyDTO.setAddress("(Pandan Indah, Ampang)");
            propertyDTO.setPrice(900000l);
            propertyDTO.setBedrooms(2);
            propertyDTO.setBathrooms(2);
            propertyDTO.setCarParks(1);
            propertyDTO.setFurnishing("Fully Furnished");
            propertyDTO.setSizeSqft(750);
            propertyDTO.setAmenities(amenities);
            propertyDTO.setCommonFacilities(commonFacilities);
            propertyDTO.setLocation("https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d15930.970407806825!2d101.68405868809338!3d3.1390036499999967!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31cc37d111111111%3A0x7a0b1d362bc3a0!2sKuala%20Lumpur%2C%20Federal%20Territory%20of%20Kuala%20Lumpur%2C%20Malaysia!5e0!3m2!1sen!2smy!4v1661430783654!5m2!1sen!2smy");

            properties.add(propertyDTO);
        }
        else if(type.equalsIgnoreCase("newProject")){
            List<String> amenities = Arrays.asList(
                    "Air conditioner",
                    "Balcony",
                    "Bed",
                    "Covered car parking",
                    "Refrigerator",
                    "Washing machine",
                    "Water heater",
                    "Wardrobe",
                    "Sofa set",
                    "Dining table",
                    "Microwave oven",
                    "TV unit",
                    "High-speed WiFi",
                    "24-hour security"
            );

            List<String> commonFacilities = new ArrayList<>(Arrays.asList(
                    "Gym",
                    "Balcony",
                    "Covered car parking",
                    "TV unit",
                    "High-speed WiFi",
                    "24-hour security"
            ));

            PropertyDTO propertyDTO = new PropertyDTO();
            propertyDTO.setId(1l);
            propertyDTO.setName("Axis Crown @ Axis Pandan");
            propertyDTO.setAddress("(Pandan Indah, Ampang)");
            propertyDTO.setPrice(500000l);
            propertyDTO.setBedrooms(2);
            propertyDTO.setBathrooms(2);
            propertyDTO.setCarParks(1);
            propertyDTO.setFurnishing("Fully Furnished");
            propertyDTO.setSizeSqft(750);
            propertyDTO.setAmenities(amenities);
            propertyDTO.setCommonFacilities(commonFacilities);
            propertyDTO.setLocation("https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d15930.970407806825!2d101.68405868809338!3d3.1390036499999967!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31cc37d111111111%3A0x7a0b1d362bc3a0!2sKuala%20Lumpur%2C%20Federal%20Territory%20of%20Kuala%20Lumpur%2C%20Malaysia!5e0!3m2!1sen!2smy!4v1661430783654!5m2!1sen!2smy");

            properties.add(propertyDTO);
        }

        return properties;
    }
}
