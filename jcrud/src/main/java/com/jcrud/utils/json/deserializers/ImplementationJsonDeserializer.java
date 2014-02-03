package com.jcrud.utils.json.deserializers;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.jcrud.utils.json.CustomJsonDeserializer;
import com.jcrud.utils.json.CustomJsonSerializer;

public class ImplementationJsonDeserializer<T> implements CustomJsonDeserializer<T>, CustomJsonSerializer<T> {

	private final Class<T> deserializingClass;

	private final Class<?> implementationClass;

	public ImplementationJsonDeserializer(Class<T> deserializingClass, Class<? extends T> implementationClass) {

		this.deserializingClass = deserializingClass;
		this.implementationClass = implementationClass;
	}

	@Override
	public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

		T object = context.deserialize(json, implementationClass);

		return object;
	}

	@Override
	public Class<T> getDeserializingClass() {
		return deserializingClass;
	}

	@Override
	public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
		
		JsonElement element = context.serialize(src);
		
		return element;
	}

	@Override
	public Class<T> getSerializingClass() {
		return deserializingClass;
	}

}
