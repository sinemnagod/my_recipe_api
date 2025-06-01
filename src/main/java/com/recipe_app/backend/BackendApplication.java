package com.recipe_app.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		String port = System.getenv("PORT");
		System.out.println(">>> ENV PORT = " + port);
	}

}
