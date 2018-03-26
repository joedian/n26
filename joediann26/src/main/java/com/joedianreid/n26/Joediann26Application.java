package com.joedianreid.n26;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Joediann26Application {

	public static void main(String[] args) {
		SpringApplication.run(Joediann26Application.class, args);
	}
}
