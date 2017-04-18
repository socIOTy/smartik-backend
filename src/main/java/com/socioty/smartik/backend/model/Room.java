package com.socioty.smartik.backend.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.MoreObjects;

public class Room {

	private String name;
	private Set<String> deviceIds;
	private byte[] imageBytes;

	protected Room() {
	}

	public Room(final String name, final byte[] imageBytes, final Set<String> deviceIds) {
		this.name = name;
		this.imageBytes = imageBytes;
		this.deviceIds = new HashSet<>(deviceIds);
	}

	public String getName() {
		return name;
	}
	
	public byte[] getImageBytes() {
		return imageBytes;
	}
	
	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}

	public Set<String> getDeviceIds() {
		return Collections.unmodifiableSet(deviceIds);
	}
	
	public void addDevice(final String deviceId) {
		this.deviceIds.add(deviceId);
	}
	
	public boolean removeDevice(final String deviceId) {
		return this.deviceIds.remove(deviceId);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("name", name).add("deviceIds", deviceIds).toString();
	}
}
