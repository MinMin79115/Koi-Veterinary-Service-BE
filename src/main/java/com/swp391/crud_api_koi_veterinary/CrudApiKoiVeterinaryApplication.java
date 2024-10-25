package com.swp391.crud_api_koi_veterinary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@ComponentScan(basePackages = {"com.swp391.crud_api_koi_veterinary"})
@OpenAPIDefinition(info = @Info(title = "Koi Veterinary API", version = "1.0", description = "API documentation for Koi Veterinary Service"))
public class CrudApiKoiVeterinaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudApiKoiVeterinaryApplication.class, args);
    }

}
