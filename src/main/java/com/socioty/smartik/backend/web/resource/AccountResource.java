package com.socioty.smartik.backend.web.resource;

import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.socioty.smartik.backend.repositories.AccountRepository;

@Path("account")
@Controller
public class AccountResource {

	@Autowired
	private AccountRepository acccuntRepository;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGet() {
		return Response.ok().entity(acccuntRepository.findAll()).build();
	}

	@OPTIONS
	public Response doOptions() {
		return Response.ok().build();
	}

	@GET
	@Path("/{email}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doGet(@PathParam("email") final String email) {
		return Response.ok().entity(acccuntRepository.findByEmail(email)).build();
	}
}
