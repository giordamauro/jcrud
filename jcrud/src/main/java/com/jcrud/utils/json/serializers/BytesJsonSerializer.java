package com.jcrud.utils.json.serializers;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.jcrud.utils.json.CustomJsonSerializer;

public class BytesJsonSerializer implements CustomJsonSerializer<byte[]> {

	@Override
	public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {

		String bytesToString = new String(src);

		JsonElement jsonElement = new JsonPrimitive(bytesToString);

		return jsonElement;
	}

	@Override
	public Class<byte[]> getSerializingClass() {
		return byte[].class;
	}

}
