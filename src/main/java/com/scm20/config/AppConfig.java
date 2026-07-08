package com.scm20.config;

import com.cloudinary.Cloudinary; // 🔑 Required for the Cloudinary instance
import com.cloudinary.utils.ObjectUtils; // 🔑 Required for mapping properties
import org.springframework.beans.factory.annotation.Value; // 🔑 Required for injecting application properties
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // 🔑 Dynamically inject keys from application.properties
    @Value("${cloudinary.cloud.name}")
    private String cloudName;

    @Value("${cloudinary.api.key}")
    private String apiKey;

    @Value("${cloudinary.api.secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        
        return new Cloudinary(
            ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
            )
        );
    }
}