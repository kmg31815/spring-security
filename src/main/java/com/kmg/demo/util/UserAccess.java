package com.kmg.demo.util;

public enum UserAccess {

	ADMIN("admin"), NORMAL("normal");

	private final String value;

	private UserAccess(String value) {
		this.value = value;
	}

	public String toString() {
		return value;
	}

}
