package com.jcrud.utils.json;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class DataTypeJsonDeserializer<T> implements CustomJsonDeserializer<T> {

	private final Class<T> deserializingClass;

	private final Map<String, CustomJsonDeserializer<T>> deserializers;

	public DataTypeJsonDeserializer(Class<T> deserializingClass, Map<String, CustomJsonDeserializer<T>> deserializers) {
		this.deserializingClass = deserializingClass;
		this.deserializers = deserializers;
	}

	@Override
	public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

		JsonObject content = json.getAsJsonObject();
		String type = content.get("type").getAsString();
		JsonElement data = content.get("data");

		T result = null;

		if (deserializers.containsKey(type)) {

			CustomJsonDeserializer<T> deserializer = deserializers.get(type);
			result = deserializer.deserialize(data, typeOfT, context);

		} else {

			throw new IllegalStateException(String.format("The type '%s' is not supported", type));
		}

		return result;
	}

	@Override
	public Class<T> getDeserializingClass() {

		return deserializingClass;
	}

}
