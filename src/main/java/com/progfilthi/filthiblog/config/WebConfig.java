package com.progfilthi.filthiblog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Value("${frontend-dev-url:http://localhost:3000}")
    private String frontendDevUrl;

    @Value("${frontend-prod-url:https://filthiblog-client.vercel.app}")
    private String frontendProdUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry
                .addMapping("/**")
                .allowedOrigins(frontendDevUrl, frontendProdUrl)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Authorization", "Content-Type","Accept")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
