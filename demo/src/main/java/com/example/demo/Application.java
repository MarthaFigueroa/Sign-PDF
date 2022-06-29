package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import Repositories.RepositoryConfig;

@SpringBootApplication(scanBasePackages = {"Controllers"})
public class Application {

	private final static Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		try {
			RepositoryConfig.initializeFirebase();
		} catch (Exception e) {
			logger.error("Unsuccessfully Initialized!",e.getMessage());
		}
		SpringApplication.run(Application.class, args);
	}
}