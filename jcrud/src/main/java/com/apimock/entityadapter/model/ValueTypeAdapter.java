package com.apimock.entityadapter.model;

public interface ValueTypeAdapter {

	<E> E valueToEntity(Object value, Class<E> entityClass);

	<V> V valueFromEntity(Object entityValue, Class<V> valueClass);
}
