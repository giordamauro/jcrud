package com.jcrud.utils.adapter;

import java.lang.reflect.Field;

import com.google.gson.Gson;

public class JsonFieldAdapter implements FieldAdapter {

	private static final Gson gson = new Gson();

	@Override
	public void adaptTo(Field srcField, Object srcFieldValue, Field targetField, Object targetObject) {

		Class<?> srcFieldClass = srcFieldValue.getClass();
		Class<?> targetFieldClass = targetField.getType();

		Object adaptedValue = getAdaptedJsonValue(srcFieldValue, srcFieldClass, targetFieldClass);

		SilentObjectCreator.setFinalPrivateField(targetObject, targetField, adaptedValue);
	}

	@Override
	public void adaptFrom(Field targetField, Object targetFieldValue, Field srcField, Object srcObject) {

		Class<?> srcFieldClass = srcField.getType();
		Class<?> targetFieldClass = targetFieldValue.getClass();

		Object adaptedValue = getAdaptedJsonValue(targetFieldValue, targetFieldClass, srcFieldClass);

		SilentObjectCreator.setFinalPrivateField(srcObject, srcField, adaptedValue);
	}

	private Object getAdaptedJsonValue(Object value, Class<?> srcFieldClass, Class<?> targetFieldClass) {

		Object adaptedValue = null;

		if (!srcFieldClass.equals(String.class) && targetFieldClass.equals(String.class)) {

			adaptedValue = gson.toJson(value);

		} else if (srcFieldClass.equals(String.class) && !targetFieldClass.equals(String.class)) {

			String json = String.valueOf(value);

			adaptedValue = gson.fromJson(json, targetFieldClass);
		} else {
			throw new IllegalStateException("Cannot adapt to json - One field must be String");
		}

		return adaptedValue;
	}

}
