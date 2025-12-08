package com.rsbusinesstech.rsbusinesstech_backend.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.util.unit.DataSize;

@Configuration
public class MultipartConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(5));          // max size per file.
        factory.setMaxRequestSize(DataSize.ofMegabytes(100)); // max request size
        factory.setFileSizeThreshold(DataSize.ofMegabytes(1));    // the size threshold after which uploaded files are written to disk instead of staying in memory.

        // Tomcat-specific: max file count
        System.setProperty("org.apache.tomcat.util.http.fileupload.FileUploadBase.fileCountMax", "20");
        return factory.createMultipartConfig();
    }
}

