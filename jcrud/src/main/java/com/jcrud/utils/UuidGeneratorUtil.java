package com.jcrud.utils;

import java.util.UUID;

import com.apimock.model.impl.MockDataDto;

public final class UuidGeneratorUtil {

	private UuidGeneratorUtil() {
	}

	public static String getUUID(String type) {

		String uuid = type + ":" + UUID.randomUUID().toString();

		return uuid;
	}

	public static String getUUID(Class<?> classType) {

		Class<?> className = classType;
		Class<?>[] interfaces = classType.getInterfaces();

		if (interfaces != null && interfaces.length == 1) {
			className = interfaces[0];
		}

		return getUUID(className.getSimpleName());
	}

	public static String getUUID(Object obj) {

		return getUUID(obj.getClass());
	}

	public static void main(String[] args) {

		System.out.println(getUUID(MockDataDto.class));
	}

}
