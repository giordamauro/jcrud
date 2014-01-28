package com.apimock.entityadapter.model;

import com.google.gson.Gson;

public class JsonTypeAdapter implements ValueTypeAdapter {

	private static final Gson gson = new Gson();

	@Override
	public <E> E valueToEntity(Object value, Class<E> entityClass) {

		if (!entityClass.equals(String.class)) {
			throw new IllegalStateException("Cannot adapt to json - Result type must be String");
		}

		String json = gson.toJson(value);

		@SuppressWarnings("unchecked")
		E returnValue = (E) json;

		return returnValue;
	}

	@Override
	public <V> V valueFromEntity(Object entityValue, Class<V> valueClass) {

		Class<?> entityClass = entityValue.getClass();

		if (!entityClass.equals(String.class)) {
			throw new IllegalStateException("Cannot adapt from json - Value must be a String");
		}

		String json = String.valueOf(entityValue);

		V returnValue = gson.fromJson(json, valueClass);

		return returnValue;
	}

}
