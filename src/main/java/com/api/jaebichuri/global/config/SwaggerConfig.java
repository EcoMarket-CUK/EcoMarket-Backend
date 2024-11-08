package com.api.jaebichuri.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String API_TITLE = "Eco Market API";
    private static final String API_VERSION = "1.0.0";

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme apiKey = new SecurityScheme()
            .type(Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .in(SecurityScheme.In.HEADER)
            .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement()
            .addList("Authorization");

        Components components = new Components()
            .addSecuritySchemes("Authorization", apiKey);

        return new OpenAPI()
            .components(components)
            .addSecurityItem(securityRequirement)
            .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
            .title(API_TITLE)
            .version(API_VERSION);
    }
}