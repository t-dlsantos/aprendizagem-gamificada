package com.grupoenzo.aprendizagem_gamificada.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Aprendizagem Gamificada").description("API responsável pela gestão de uma plataforma de ensino gamificada").version("1.0"))
        ;
    }

}


