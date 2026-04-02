package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/powerbi/**")
                .allowedOrigins("https://fdev.compactortech.com.br")
                .allowedMethods("GET","OPTIONS")
                .allowedHeaders("*");
    }
}
