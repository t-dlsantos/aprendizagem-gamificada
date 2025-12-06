package com.grupoenzo.aprendizagem_gamificada;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AprendizagemGamificadaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AprendizagemGamificadaApplication.class, args);
	}

}