package com.jcrud.utils.adapter;

import java.lang.reflect.Field;

public interface FieldAdapter {

	void adaptTo(Field srcField, Object srcFieldValue, Field targetField, Object targetObject);

	void adaptFrom(Field targetField, Object targetFieldValue, Field srcField, Object srcObject);
}
