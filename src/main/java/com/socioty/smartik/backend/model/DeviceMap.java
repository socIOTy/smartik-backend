package com.socioty.smartik.backend.model;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.MoreObjects;

public class DeviceMap {

	public List<Floor> floors;

	public DeviceMap() {
	}

	public DeviceMap(final List<Floor> floors) {
		this.floors = new ArrayList<>(floors);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("floors", floors).toString();
	}
}
