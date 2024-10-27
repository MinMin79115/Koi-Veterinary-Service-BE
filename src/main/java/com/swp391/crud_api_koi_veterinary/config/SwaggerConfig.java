package com.swp391.crud_api_koi_veterinary.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Koi Veterinary API")
                        .version("3.0")
                        .description("API documentation for Koi Veterinary Service"));
    }
}
