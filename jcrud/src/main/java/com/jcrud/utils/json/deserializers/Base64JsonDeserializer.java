package com.jcrud.utils.json.deserializers;

import java.lang.reflect.Type;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.jcrud.utils.json.CustomJsonDeserializer;

public class Base64JsonDeserializer implements CustomJsonDeserializer<byte[]> {

	@Override
	public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

		String data = json.getAsString();

		byte[] bytes = getFromBase64(data);

		return bytes;
	}

	@Override
	public Class<byte[]> getDeserializingClass() {
		return byte[].class;
	}

	private byte[] getFromBase64(String base64String) {

		byte[] decodedBytes = Base64.decodeBase64(base64String);

		return decodedBytes;
	}

}
