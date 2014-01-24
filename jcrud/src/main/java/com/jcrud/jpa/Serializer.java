package com.jcrud.jpa;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public final class Serializer {

	public static <T extends Serializable> T getObject(byte[] bytes, String className) {

		try {
			@SuppressWarnings("unchecked")
			Class<T> typeClass = (Class<T>) Class.forName(className);
			T object = getObject(bytes, typeClass);

			return object;

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public static <T extends Serializable> T getObject(byte[] bytes, Class<T> type) {
		if (bytes == null) {
			return null;
		}

		try {
			ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
			ObjectInputStream in = new ObjectInputStream(byteIn);

			@SuppressWarnings("unchecked")
			T obj = (T) in.readObject();
			in.close();

			return obj;

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public static byte[] getBytes(Object object) {

		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
			objOut.writeObject(object);
			objOut.close();
			byteOut.close();
			byte[] bytes = byteOut.toByteArray();

			return bytes;

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
