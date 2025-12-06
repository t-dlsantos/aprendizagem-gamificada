package com.grupoenzo.recommender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableAsync
public class RecommenderApplication {
    public static void main(String[] args) {
        // Tenta carregar variáveis de ambiente de múltiplos locais
        loadEnv("./");
        loadEnv("../");
        loadEnv("./.env");
        loadEnv("../.env");

        SpringApplication.run(RecommenderApplication.class, args); 
    }

    private static void loadEnv(String path) {
        try {
            Dotenv dotenv = Dotenv.configure()
                .directory(path)
                .ignoreIfMissing()
                .load();
            
            dotenv.entries().forEach(entry -> {
                if (System.getProperty(entry.getKey()) == null) {
                    System.setProperty(entry.getKey(), entry.getValue());
                }
            });
        } catch (Exception e) {
            // Ignora falhas de carregamento
        }
    }
}
