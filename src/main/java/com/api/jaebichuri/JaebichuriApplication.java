package com.api.jaebichuri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JaebichuriApplication {

	public static void main(String[] args) {
		SpringApplication.run(JaebichuriApplication.class, args);
	}

}
