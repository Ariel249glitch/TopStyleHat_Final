package com.example.BloqueGorro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
            new Info()
                .title("API Gorros - Top Style")
                .version("1.0")
                .description("Informacion del microservicio de Gorro de la tienda Top Style")
        );
    }

    //link swagger
    //http://localhost:8080/doc/swagger-ui.html
}
