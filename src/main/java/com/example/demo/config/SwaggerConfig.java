package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI commentAppOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Employee Comment Management API")
                        .description("API documentation for managing employees and their comments")
                        .version("1.0"));
    }
}
