package com.sw.mathapp.MathApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.sw.mathapp")
public class MathAppApplication {

	public static void main(final String[] args) {
		SpringApplication.run(MathAppApplication.class, args);
	}
}
