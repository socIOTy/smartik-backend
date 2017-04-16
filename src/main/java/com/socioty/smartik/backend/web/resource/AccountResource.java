package com.socioty.smartik.backend.web.resource;

import java.util.Collections;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.socioty.smartik.backend.model.Account;
import com.socioty.smartik.backend.model.DeviceMap;
import com.socioty.smartik.backend.repositories.AccountRepository;

@Path("account")
@Controller
public class AccountResource {
	
	@XmlRootElement
	@SuppressWarnings("unused")
	private static class AddAccountJson {
		private String email;

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
	}

	@Autowired
	private AccountRepository accountRepository;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGet() {
		return Response.ok().entity(accountRepository.findAll()).build();
	}

	@OPTIONS
	public Response doOptions() {
		return Response.ok().build();
	}

	@GET
	@Path("/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGet(@PathParam("email") final String email) {
		return Response.ok().entity(accountRepository.findByEmail(email)).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doPost(final AddAccountJson account) {
		final Account result = new Account(account.getEmail(), new DeviceMap(Collections.emptyList()));
		return Response.ok().entity(accountRepository.insert(result)).build();
	}
	
}
