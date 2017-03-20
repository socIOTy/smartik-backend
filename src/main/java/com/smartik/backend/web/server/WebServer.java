package com.smartik.backend.web.server;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.smartik.backend.model.Account;
import com.smartik.backend.model.DeviceMap;
import com.smartik.backend.model.Floor;
import com.smartik.backend.repositories.AccountRepository;

import jersey.repackaged.com.google.common.collect.Lists;

@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = { "com.smartik.backend.repositories" })
@ComponentScan(basePackages = { "com.smartik.backend.web.resource" })
public class WebServer implements CommandLineRunner {

	@Autowired
	private AccountRepository repository;

	public static void main(final String[] args) throws Exception {
		// LoggingSystem.get(null).setLogLevel("br.com.noxxonsat.tripconsolidator",
		// LogLevel.DEBUG);
		// LoggingSystem.get(null).setLogLevel("br.com.noxxonsat.tripconsolidator.datatransfer",
		// LogLevel.DEBUG);
		SpringApplication.run(WebServer.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		repository.deleteAll();

		// save a couple of customers
		repository.save(new Account("willian.campos@gmail.com",
				new DeviceMap(Lists.newArrayList(
						new Floor(Collections.singletonMap("Living room",
								Lists.newArrayList("Bulb 1", "Bulb 2", "Thermostat"))),
						new Floor(Collections.singletonMap("Bedroom", Lists.newArrayList("Bulb", "TV")))))));

		// fetch all customers
		System.out.println("Accounts found with findAll():");
		System.out.println("-------------------------------");
		for (Account account : repository.findAll()) {
			System.out.println(account);
		}
		System.out.println();

	}

	// @Bean
	// public EmbeddedServletContainerCustomizer containerCustomizer() throws
	// FileNotFoundException {
	// if (Boolean.parseBoolean(environment.getProperty("ssl.use.keystore"))) {
	// final String absoluteKeystoreFile =
	// ResourceUtils.getFile(environment.getProperty("ssl.keystore.path"))
	// .getAbsolutePath();
	//
	// return new EmbeddedServletContainerCustomizer() {
	// @Override
	// public void customize(final ConfigurableEmbeddedServletContainer factory)
	// {
	// if (factory instanceof TomcatEmbeddedServletContainerFactory) {
	// final TomcatEmbeddedServletContainerFactory containerFactory =
	// (TomcatEmbeddedServletContainerFactory) factory;
	// containerFactory.addConnectorCustomizers(new TomcatConnectorCustomizer()
	// {
	// @Override
	// public void customize(final Connector connector) {
	// connector.setPort(443);
	// connector.setSecure(true);
	// connector.setScheme("https");
	// final Http11NioProtocol proto = (Http11NioProtocol)
	// connector.getProtocolHandler();
	// proto.setSSLEnabled(true);
	// proto.setKeystoreFile(absoluteKeystoreFile);
	// proto.setKeystorePass(environment.getProperty("ssl.keystore.password"));
	// proto.setKeystoreType("JKS");
	// }
	// });
	// }
	// }
	// };
	// }
	//
	// return new EmbeddedServletContainerCustomizer() {
	// @Override
	// public void customize(ConfigurableEmbeddedServletContainer arg0) {
	// }
	// };
	//
	// }

}