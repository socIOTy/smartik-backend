package com.socioty.smartik.backend.web.resource;

import static com.socioty.smartik.backend.util.Utils.authenticatedEmail;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import com.socioty.smartik.backend.model.ModelUtils;
import com.socioty.smartik.backend.repositories.AccountRepository;

@Path("device")
@Controller
public class DeviceResource {

	@XmlRootElement
	@SuppressWarnings("unused")
	private static class AddDeviceJson {
		private String deviceId;
		private Integer floorNumber;
		private String roomName;

		public String getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}

		public Integer getFloorNumber() {
			return floorNumber;
		}

		public void setFloorNumber(Integer floorNumber) {
			this.floorNumber = floorNumber;
		}

		public String getRoomName() {
			return roomName;
		}

		public void setRoomName(String roomName) {
			this.roomName = roomName;
		}

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
	public Response doPost(final AddDeviceJson addDevice) {
		final Account account = accountRepository.findByEmail(authenticatedEmail());
		if (account == null) {
			return Response.status(404).build();
		}
		ModelUtils.removeDevice(account.getDeviceMap(), addDevice.deviceId);
		ModelUtils.getRoom(account.getDeviceMap().getFloors().get(addDevice.floorNumber).getRooms(), addDevice.roomName)
				.addDevice(addDevice.deviceId);
		return Response.ok().entity(accountRepository.save(account)).build();
	}

	@DELETE
	@Path("/{deviceId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doPost(@PathParam("deviceId") final String deviceId) {
		final Account account = accountRepository.findByEmail(authenticatedEmail());
		if (account != null && ModelUtils.removeDevice(account.getDeviceMap(), deviceId)) {
			return Response.ok().entity(accountRepository.save(account)).build();
		}

		return Response.status(404).build();
	}
}
