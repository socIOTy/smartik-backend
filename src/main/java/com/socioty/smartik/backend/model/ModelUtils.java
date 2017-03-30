package com.socioty.smartik.backend.model;

import java.util.Set;

public final class ModelUtils {

	private ModelUtils() {
		throw new AssertionError();
	}
	
	public static Room getRoom(final Set<Room> rooms, final String name) {
		for (final Room room : rooms) {
			if (room.getName().equals(name)) {
				return room;
			}
		}
		
		return null;
	}
	
	public static boolean removeDevice(final DeviceMap map, final String deviceId) {
		for (final Floor floor : map.getFloors()) {
			for (final Room room : floor.getRooms()) {
				if (room.removeDevice(deviceId)) {
					return true;
				}
			}
		}
		return false;
	}
}
