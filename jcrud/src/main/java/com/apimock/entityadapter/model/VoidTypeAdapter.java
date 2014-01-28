package com.apimock.entityadapter.model;

public class VoidTypeAdapter implements ValueTypeAdapter {

	@Override
	public <E> E valueToEntity(Object value, Class<E> entityClass) {

		@SuppressWarnings("unchecked")
		E entityValue = (E) value;

		return entityValue;
	}

	@Override
	public <V> V valueFromEntity(Object entityValue, Class<V> valueClass) {

		@SuppressWarnings("unchecked")
		V value = (V) entityValue;

		return value;
	}

}
