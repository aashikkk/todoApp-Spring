package com.aashik.todorest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodorestApplication {

	private static final Logger log = LoggerFactory.getLogger(TodorestApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TodorestApplication.class, args);
	}

	CommandLineRunner runner(){
		return args -> {
			log.info("Welcome to Spring boot Application");
		};
	}

}
