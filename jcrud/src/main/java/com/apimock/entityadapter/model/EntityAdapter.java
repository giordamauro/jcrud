package com.apimock.entityadapter.model;

public interface EntityAdapter {

	void registerClassEntity(Class<?> richObjectClass, Class<?> entityClass);

	void registerByAnnotations(Class<?> richObjectClass);

	<T> T toEntity(Object richObject);

	<T> T fromEntity(Object entityObject);
}
