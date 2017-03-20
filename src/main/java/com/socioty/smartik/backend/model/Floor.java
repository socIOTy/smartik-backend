package com.socioty.smartik.backend.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.MoreObjects;

public class Floor {

	public Map<String, List<String>> devicesByRoom;

	public Floor() {
	}

	public Floor(final Map<String, List<String>> devicesByRoom) {
		this.devicesByRoom = new HashMap<>(devicesByRoom);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("devicesByRoom", devicesByRoom).toString();
	}
}
