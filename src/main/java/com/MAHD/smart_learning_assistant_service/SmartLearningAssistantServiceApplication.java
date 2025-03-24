package com.MAHD.smart_learning_assistant_service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmartLearningAssistantServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(SmartLearningAssistantServiceApplication.class, args);
	}

}
