package com.lms.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.lms")
public class LmsApplication {

	public static void main(String[] args1) {
		SpringApplication.run(LmsApplication.class, args1);
	}

}
