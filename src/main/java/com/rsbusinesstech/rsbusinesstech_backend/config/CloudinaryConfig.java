package com.rsbusinesstech.rsbusinesstech_backend.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary getCloudinary(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name","divjzb0t3",
                "api_key", "783475113858961",
                "api_secret", "WWI1IJlm9eIjHZmkublSMH__N7A"
        ));
    }
}
