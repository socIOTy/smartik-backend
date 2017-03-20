package com.socioty.smartik.backend.web.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.google.common.collect.Sets;
import com.socioty.smartik.backend.model.Account;
import com.socioty.smartik.backend.model.DeviceMap;
import com.socioty.smartik.backend.model.Floor;
import com.socioty.smartik.backend.model.Room;
import com.socioty.smartik.backend.repositories.AccountRepository;

import jersey.repackaged.com.google.common.collect.Lists;

@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = { "com.socioty.smartik.backend.repositories" })
@ComponentScan(basePackages = { "com.socioty.smartik.backend.web.resource" })
public class WebServer implements CommandLineRunner {

	@Autowired
	private AccountRepository repository;

	public static void main(final String[] args) throws Exception {
		LoggingSystem.get(null).setLogLevel("com.socioty.smartik.backend", LogLevel.DEBUG);
		SpringApplication.run(WebServer.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		repository.deleteAll();

		// save a account
		repository.save(new Account("willian.campos@gmail.com",
				new DeviceMap(Lists.newArrayList(
						new Floor(Sets.newHashSet(
								new Room("Living room", Sets.newHashSet("Bulb 1", "Bulb 2", "Thermostat")))),
						new Floor(Sets.newHashSet(new Room("Bedroom", Sets.newHashSet("Bulb", "TV"))))))));

		// fetch all accounts
		System.out.println("Accounts found with findAll():");
		System.out.println("-------------------------------");
		for (Account account : repository.findAll()) {
			System.out.println(account);
		}
		System.out.println();

	}
}