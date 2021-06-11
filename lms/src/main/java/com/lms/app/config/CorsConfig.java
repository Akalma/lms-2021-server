package com.lms.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
    	String mappingPattern = "/**";
    	registry
    		.addMapping(mappingPattern).allowedMethods("HEAD","PUT","POST","GET","DELETE","OPTIONS","PATCH").
				allowedOrigins("*");
    		//.allowedOrigins("http://localhost:8080");//should allow from anywhere
    	log.info(String.format("CORS configuration set to %s for mapping %s", "", mappingPattern));
    }
}