package com.example.backend.spring.boot.soap.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI soapOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SOAP User CRUD API")
                        .version("1.0.0")
                        .description("SOAP-based User Management Service with Full CRUD"))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Server")
                ));
    }
}
