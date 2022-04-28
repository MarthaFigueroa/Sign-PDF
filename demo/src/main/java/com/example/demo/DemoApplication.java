package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import Services.FirebaseStorageStrategy;

@SpringBootApplication(scanBasePackages = {"Controllers"})
public class DemoApplication {

	public static void main(String[] args) {
		try {
			FirebaseStorageStrategy.initializeFirebase();
		} catch (Exception e) {
			System.out.println("Unsuccessfully Initialized! "+e.getMessage());
		}
		SpringApplication.run(DemoApplication.class, args);
	}
}
