package com.socioty.smartik.backend.util;

import org.apache.shiro.SecurityUtils;

public final class Utils {

	private Utils() {
		throw new AssertionError();
	}
	
	public static String authenticatedEmail() {
		return SecurityUtils.getSubject().getPrincipal().toString();
	}
	
	
}
