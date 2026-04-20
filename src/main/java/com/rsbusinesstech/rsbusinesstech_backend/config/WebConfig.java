package com.rsbusinesstech.rsbusinesstech_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200",                                                 //allowed Origins.
                                        "https://www.rsbusinesstech.com",      "https://rsbusinesstech.com" ,    //because both are different origins in browser's eye.
                                        "https://www.rssupermart.com",         "https://rssupermart.com" ,
                                        "https://www.lashmapbeautystudio.com", "https://lashmapbeautystudio.com",
                                        "https://www.vyenpropertyadvisor.com", "https://vyenpropertyadvisor.com",
                                        "https://rsbusinesstech.netlify.app")                                     //for HttpOnly cookie, used in JWT authentication.
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  //allowed methods.  [OPTIONS - Browser sends preflight requests to server to verify CORS before sending actual request.]
                        /*
                           • by default, backend server does not accept credentials(like cookies, HTTP auth headers, or other credentials) received in cross-origin requests.
                           • setting .allowCredentials(true) allows the backend server to accept credentials from frontend, include credentials in it's response.
                        */
                        .allowCredentials(true);
            }
        };
    }
}

