package com.blog.app.utils;

import java.util.UUID;

public class IdGenerator {

	public static String GetUniqueId() {
		UUID uuid = UUID.randomUUID();
		String uniqueID = uuid.toString();
		return uniqueID;
	}
}
