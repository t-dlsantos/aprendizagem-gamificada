package com.grupoenzo.recommender.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Recommender API")
                        .description("API de sistema de recomendação de cursos usando RAG e embeddings")
                        .version("1.0"));
    }
}

