package com.jcrud.utils.json.deserializers;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.jcrud.utils.json.CustomJsonDeserializer;

public class StringJsonDeserializer implements CustomJsonDeserializer<byte[]> {

	@Override
	public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

		String data = json.getAsString();

		byte[] bytes = getFromString(data);

		return bytes;
	}

	@Override
	public Class<byte[]> getDeserializingClass() {
		return byte[].class;
	}

	private byte[] getFromString(String stringContent) {

		try {
			byte[] bytes = stringContent.getBytes("UTF-8");

			return bytes;

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

}
