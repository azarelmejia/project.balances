package com.azarel.balances;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.azarel.balances")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
