package com.example.BFF1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.BFF1")

public class Bff1Application {

	public static void main(String[] args) {
		SpringApplication.run(Bff1Application.class, args);
	}

}
