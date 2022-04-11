package com.hangaries;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HangariesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HangariesApplication.class, args);
	}
}
