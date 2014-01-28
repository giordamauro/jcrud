package com.jcrud.utils.adapter;

public interface ClassAdapter {

	<T> T adaptTo(Object srcObject, Class<T> targetClass);

	<T> T adaptFrom(Object targetObject, Class<T> srcClass);

}
