package com.grupoenzo.recommender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RecommenderApplication {
    public static void main(String[] args) { SpringApplication.run(RecommenderApplication.class, args); }
}
