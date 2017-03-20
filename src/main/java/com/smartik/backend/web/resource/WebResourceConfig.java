package com.smartik.backend.web.resource;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@ApplicationPath("/rest")
@Configuration
public class WebResourceConfig extends ResourceConfig {
	
	public WebResourceConfig() {
		register(AccountResource.class);
	}
}
