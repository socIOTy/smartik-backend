package com.socioty.smartik.backend.web.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = { "com.socioty.smartik.backend.repositories" })
@ComponentScan(basePackages = { "com.socioty.smartik.backend.web.resource" })
public class WebServer implements CommandLineRunner {

	public static void main(final String[] args) throws Exception {
		SpringApplication.run(WebServer.class, args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
	}

}