package com.socioty.smartik.backend.web.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.socioty.smartik.backend.model.Account;
import com.socioty.smartik.backend.model.Floor;
import com.socioty.smartik.backend.model.ModelUtils;
import com.socioty.smartik.backend.model.Room;
import com.socioty.smartik.backend.repositories.AccountRepository;

@Path("device")
@Controller
public class DeviceResource {
	
	private static class AddDevice {
		private String deviceId;
		private Integer floorNumber;
		private String roomName;
	}

	@Autowired
	private AccountRepository accountRepository;

	@OPTIONS
	public Response doOptions() {
		return Response.ok().build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doPost(final AddDevice addDevice) {
		//TODO retrieve email from authentication
		final String email = "socioty.centennial@gmail.com";
		
		final Account account = accountRepository.findByEmail(email);
		ModelUtils.getRoom(account.getDeviceMap().getFloors().get(addDevice.floorNumber).getRooms(), addDevice.roomName).addDevice(addDevice.deviceId);
		return Response.ok().entity(accountRepository.save(account)).build();
	}
	
	@DELETE
	public Response doPost(@QueryParam("deviceId") final String deviceId) {
		//TODO retrieve email from authentication
		final String email = "socioty.centennial@gmail.com";
		
		final Account account = accountRepository.findByEmail(email);
		
		for (final Floor floor : account.getDeviceMap().getFloors()) {
			for (final Room room : floor.getRooms()) {
				if (room.removeDevice(deviceId)) {
					return Response.ok().build();
				}
			}
		}
		return Response.status(404).build();
		
	}
}
