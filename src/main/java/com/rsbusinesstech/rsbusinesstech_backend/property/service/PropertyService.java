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

        List<String> amenities = Arrays.asList(
                "Private bathroom",
                "Family room",
                "Flat screen TV",
                "Balcony",
                "Air conditioning",
                "Kitchen",
                "Washing machine",
                "Pets allowed",
                "Kitchen",
                "Dining table",
                "Microwave oven",
                "Sofa",
                "High-speed WiFi",
                "24-hour security"
        );

        List<String> commonFacilities = new ArrayList<>(Arrays.asList(
                "Gym",
                "Outdoor swimming pool",
                "Sauna",
                "Terrace",
                "Park",
                "Water slide",
                "24-hour security"
        ));

        List<String> imageUrls = new ArrayList<>(Arrays.asList(
                "assets/images/commercial-hero.webp",
                "assets/images/kl.webp",
                "assets/images/kl-rent-bg.webp",
                "assets/images/commercial-hero.webp",
                "assets/images/buy-hero.webp",
                "assets/images/kl-rent-bg.webp"
        ));

        if(type.equalsIgnoreCase("rent")){
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
            propertyDTO.setImageUrls(imageUrls);

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
            propertyDTO2.setImageUrls(imageUrls);

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
            propertyDTO3.setImageUrls(imageUrls);

            properties.add(propertyDTO);
            properties.add(propertyDTO2);
            properties.add(propertyDTO3);

        }else if(type.equalsIgnoreCase("buy")){
            List<String> imageUrlsLavile = new ArrayList<>(Arrays.asList(
                    "assets/images/lavile1.jpeg",
                    "assets/images/lavile2.jpeg",
                    "assets/images/lavile3.jpeg",
                    "assets/images/lavile4.jpeg",
                    "assets/images/lavile5.jpeg",
                    "assets/images/lavile6.jpeg",
                    "assets/images/lavile7.jpeg",
                    "assets/images/lavile8.jpeg",
                    "assets/images/lavile9.jpeg",
                    "assets/images/lavile10.jpeg",
                    "assets/images/lavile11.jpeg"
                    ));

            PropertyDTO propertyDTO1 = new PropertyDTO();
            propertyDTO1.setId(1l);
            propertyDTO1.setName("Lavile Residence");
            propertyDTO1.setAddress("(Kuala Lumpur, Malaysia)");
            propertyDTO1.setPrice(0l);
            propertyDTO1.setBedrooms(4);
            propertyDTO1.setBathrooms(3);
            propertyDTO1.setCarParks(5);
            propertyDTO1.setFurnishing("Partly Furnished");
            propertyDTO1.setSizeSqft(2080);
            propertyDTO1.setAmenities(amenities);
            propertyDTO1.setCommonFacilities(commonFacilities);
            propertyDTO1.setLocation("https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d15930.970407806825!2d101.68405868809338!3d3.1390036499999967!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31cc37d111111111%3A0x7a0b1d362bc3a0!2sKuala%20Lumpur%2C%20Federal%20Territory%20of%20Kuala%20Lumpur%2C%20Malaysia!5e0!3m2!1sen!2smy!4v1661430783654!5m2!1sen!2smy");
            propertyDTO1.setImageUrls(imageUrlsLavile);

            PropertyDTO propertyDTO2 = new PropertyDTO();
            propertyDTO2.setId(2l);
            propertyDTO2.setName("Lavile Residence");
            propertyDTO2.setAddress("(Kuala Lumpur, Malaysia)");
            propertyDTO2.setPrice(938000l);
            propertyDTO2.setBedrooms(3);
            propertyDTO2.setBathrooms(2);
            propertyDTO2.setCarParks(2);
            propertyDTO2.setFurnishing("Fully Furnished");
            propertyDTO2.setSizeSqft(1067);
            propertyDTO2.setAmenities(amenities);
            propertyDTO2.setCommonFacilities(commonFacilities);
            propertyDTO2.setLocation("https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d15930.970407806825!2d101.68405868809338!3d3.1390036499999967!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31cc37d111111111%3A0x7a0b1d362bc3a0!2sKuala%20Lumpur%2C%20Federal%20Territory%20of%20Kuala%20Lumpur%2C%20Malaysia!5e0!3m2!1sen!2smy!4v1661430783654!5m2!1sen!2smy");
            propertyDTO2.setImageUrls(imageUrlsLavile);

            PropertyDTO propertyDTO3 = new PropertyDTO();
            propertyDTO3.setId(3l);
            propertyDTO3.setName("Lavile Residence");
            propertyDTO3.setAddress("(Kuala Lumpur, Malaysia)");
            propertyDTO3.setPrice(830000l);
            propertyDTO3.setBedrooms(3);
            propertyDTO3.setBathrooms(2);
            propertyDTO3.setCarParks(2);
            propertyDTO3.setFurnishing("Partly Furnished");
            propertyDTO3.setSizeSqft(956);
            propertyDTO3.setAmenities(amenities);
            propertyDTO3.setCommonFacilities(commonFacilities);
            propertyDTO3.setLocation("https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d15930.970407806825!2d101.68405868809338!3d3.1390036499999967!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31cc37d111111111%3A0x7a0b1d362bc3a0!2sKuala%20Lumpur%2C%20Federal%20Territory%20of%20Kuala%20Lumpur%2C%20Malaysia!5e0!3m2!1sen!2smy!4v1661430783654!5m2!1sen!2smy");
            propertyDTO3.setImageUrls(imageUrlsLavile);

            PropertyDTO propertyDTO4 = new PropertyDTO();
            propertyDTO4.setId(4l);
            propertyDTO4.setName("Lavile Residence");
            propertyDTO4.setAddress("(Kuala Lumpur, Malaysia)");
            propertyDTO4.setPrice(735000l);
            propertyDTO4.setBedrooms(3);
            propertyDTO4.setBathrooms(2);
            propertyDTO4.setCarParks(2);
            propertyDTO4.setFurnishing("Fully Furnished");
            propertyDTO4.setSizeSqft(865);
            propertyDTO4.setAmenities(amenities);
            propertyDTO4.setCommonFacilities(commonFacilities);
            propertyDTO4.setLocation("https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d15930.970407806825!2d101.68405868809338!3d3.1390036499999967!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31cc37d111111111%3A0x7a0b1d362bc3a0!2sKuala%20Lumpur%2C%20Federal%20Territory%20of%20Kuala%20Lumpur%2C%20Malaysia!5e0!3m2!1sen!2smy!4v1661430783654!5m2!1sen!2smy");
            propertyDTO4.setImageUrls(imageUrlsLavile);

            properties.add(propertyDTO1);
            properties.add(propertyDTO2);
            properties.add(propertyDTO3);
            properties.add(propertyDTO4);
        }
        else if(type.equalsIgnoreCase("commercial")){
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
            propertyDTO.setImageUrls(imageUrls);

            PropertyDTO propertyDTO2 = new PropertyDTO();
            propertyDTO2.setId(2l);
            propertyDTO2.setName("Axis Crown @ Axis Pandan");
            propertyDTO2.setAddress("(Pandan Indah, Ampang)");
            propertyDTO2.setPrice(900000l);
            propertyDTO2.setBedrooms(3);
            propertyDTO2.setBathrooms(2);
            propertyDTO2.setCarParks(3);
            propertyDTO2.setFurnishing("Fully Furnished");
            propertyDTO2.setSizeSqft(750);
            propertyDTO2.setAmenities(amenities);
            propertyDTO2.setCommonFacilities(commonFacilities);
            propertyDTO2.setLocation("https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d15930.970407806825!2d101.68405868809338!3d3.1390036499999967!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31cc37d111111111%3A0x7a0b1d362bc3a0!2sKuala%20Lumpur%2C%20Federal%20Territory%20of%20Kuala%20Lumpur%2C%20Malaysia!5e0!3m2!1sen!2smy!4v1661430783654!5m2!1sen!2smy");
            propertyDTO2.setImageUrls(imageUrls);

            properties.add(propertyDTO);
            properties.add(propertyDTO2);
        }
        else if(type.equalsIgnoreCase("mm2h")){
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
            propertyDTO.setImageUrls(imageUrls);

            PropertyDTO propertyDTO2 = new PropertyDTO();
            propertyDTO2.setId(2l);
            propertyDTO2.setName("Axis Crown @ Axis Pandan");
            propertyDTO2.setAddress("(Pandan Indah, Ampang)");
            propertyDTO2.setPrice(950000l);
            propertyDTO2.setBedrooms(3);
            propertyDTO2.setBathrooms(2);
            propertyDTO2.setCarParks(4);
            propertyDTO2.setFurnishing("Fully Furnished");
            propertyDTO2.setSizeSqft(750);
            propertyDTO2.setAmenities(amenities);
            propertyDTO2.setCommonFacilities(commonFacilities);
            propertyDTO2.setLocation("https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d15930.970407806825!2d101.68405868809338!3d3.1390036499999967!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31cc37d111111111%3A0x7a0b1d362bc3a0!2sKuala%20Lumpur%2C%20Federal%20Territory%20of%20Kuala%20Lumpur%2C%20Malaysia!5e0!3m2!1sen!2smy!4v1661430783654!5m2!1sen!2smy");
            propertyDTO2.setImageUrls(imageUrls);

            properties.add(propertyDTO);
            properties.add(propertyDTO2);
        }
        else if(type.equalsIgnoreCase("newProjects")){
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
            propertyDTO.setImageUrls(imageUrls);

            PropertyDTO propertyDTO2 = new PropertyDTO();
            propertyDTO2.setId(2l);
            propertyDTO2.setName("Axis Crown @ Axis Pandan");
            propertyDTO2.setAddress("(Pandan Indah, Ampang)");
            propertyDTO2.setPrice(800000l);
            propertyDTO2.setBedrooms(4);
            propertyDTO2.setBathrooms(2);
            propertyDTO2.setCarParks(3);
            propertyDTO2.setFurnishing("Fully Furnished");
            propertyDTO2.setSizeSqft(750);
            propertyDTO2.setAmenities(amenities);
            propertyDTO2.setCommonFacilities(commonFacilities);
            propertyDTO2.setLocation("https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d15930.970407806825!2d101.68405868809338!3d3.1390036499999967!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31cc37d111111111%3A0x7a0b1d362bc3a0!2sKuala%20Lumpur%2C%20Federal%20Territory%20of%20Kuala%20Lumpur%2C%20Malaysia!5e0!3m2!1sen!2smy!4v1661430783654!5m2!1sen!2smy");
            propertyDTO2.setImageUrls(imageUrls);

            properties.add(propertyDTO);
            properties.add(propertyDTO2);
        }

        return properties;
    }
}
