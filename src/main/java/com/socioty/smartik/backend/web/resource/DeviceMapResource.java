package com.socioty.smartik.backend.web.resource;

import static com.socioty.smartik.backend.util.Utils.authenticatedEmail;

import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.socioty.smartik.backend.model.Account;
import com.socioty.smartik.backend.model.DeviceMap;
import com.socioty.smartik.backend.model.Floor;
import com.socioty.smartik.backend.model.Room;
import com.socioty.smartik.backend.repositories.AccountRepository;

@Path("deviceMap")
@Controller
public class DeviceMapResource {
	
	@XmlRootElement
	@SuppressWarnings("unused")
	private static class DeviceMapJson {
		private static final Function<DeviceMapJson, DeviceMap> transformFunction =  new Function<DeviceMapJson,DeviceMap>()  {
			@Override
			public DeviceMap apply(final DeviceMapJson original) {
				return new DeviceMap(FluentIterable.from(original.floors).transform(FloorJson.transformFunction).toList());
			}
		};
		
		private List<FloorJson> floors;

		public List<FloorJson> getFloors() {
			return floors;
		}

		public void setFloors(List<FloorJson> floors) {
			this.floors = floors;
		}
	}
	
	@SuppressWarnings("unused")
	private static class FloorJson {
		private static final Function<FloorJson, Floor> transformFunction =  new Function<FloorJson,Floor>()  {
			@Override
			public Floor apply(final FloorJson original) {
				return new Floor(FluentIterable.from(original.rooms).transform(RoomJson.transformFunction).toSet());
			}
		};
		
		private Set<RoomJson> rooms;

		public Set<RoomJson> getRooms() {
			return rooms;
		}

		public void setRooms(Set<RoomJson> rooms) {
			this.rooms = rooms;
		}
	}
	
	@SuppressWarnings("unused")
	private static class RoomJson {
		private static final Function<RoomJson, Room> transformFunction =  new Function<RoomJson, Room>()  {
			@Override
			public Room apply(final RoomJson original) {
				return new Room(original.name, original.imageBytes, original.deviceIds);
			}
		};
		
		private String name;
		private Set<String> deviceIds;
		private byte[] imageBytes;
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Set<String> getDeviceIds() {
			return deviceIds;
		}
		public void setDeviceIds(Set<String> deviceIds) {
			this.deviceIds = deviceIds;
		}
		public byte[] getImageBytes() {
			return imageBytes;
		}
		public void setImageBytes(byte[] imageBytes) {
			this.imageBytes = imageBytes;
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
	public Response doPost(final DeviceMapJson json) {
		final Account account = accountRepository.findByEmail(authenticatedEmail());
		if (account == null) {
			return Response.status(404).build();
		}
		account.setDeviceMap(DeviceMapJson.transformFunction.apply(json));
		return Response.ok().entity(accountRepository.save(account)).build();
	}
	
}
