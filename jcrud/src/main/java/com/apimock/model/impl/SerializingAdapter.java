package com.apimock.model.impl;

import java.lang.reflect.Field;

import com.jcrud.jpa.Serializer;
import com.jcrud.utils.adapter.FieldAdapter;
import com.jcrud.utils.adapter.SilentObjectCreator;

public class SerializingAdapter implements FieldAdapter {

	@Override
	public void adaptTo(Field srcField, Object srcFieldValue, Field targetField, Object targetObject) {

		byte[] bytes = Serializer.getBytes(srcFieldValue);
		String className = srcFieldValue.getClass().getName();

		SilentObjectCreator.setFinalPrivateField(targetObject, targetField, bytes);
		SilentObjectCreator.setFinalPrivateField(targetObject, "className", className);
	}

	@Override
	public void adaptFrom(Field targetField, Object targetFieldValue, Object targetObject, Field srcField, Object srcObject) {

		byte[] bytes = (byte[]) targetFieldValue;

		String className = (String) SilentObjectCreator.getFinalPrivateField(targetObject, "className");
		Object deserialized = Serializer.getObject(bytes, className);

		SilentObjectCreator.setFinalPrivateField(srcObject, srcField, deserialized);

	}

}
