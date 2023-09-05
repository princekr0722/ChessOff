package com.chessoff.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer{
	@Value("${allowed.frontend.origin.web}")
	private String allowedFrontendOriginWeb;
	
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // Define your API path pattern here
        .allowedOrigins(allowedFrontendOriginWeb) // Define the allowed origins (domains)
        .allowedMethods("*") // Define the HTTP methods allowed
        .allowCredentials(true); // Allow cookies, if needed
	}
}
