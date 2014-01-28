package com.apimock.entityadapter.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import sun.reflect.ReflectionFactory;

@SuppressWarnings("restriction")
public final class SilentObjectCreator {

	public static <T> T create(Class<T> clazz) {
		return create(clazz, Object.class);
	}

	public static <T> T create(Class<T> clazz, Class<? super T> parent) {
		try {
			ReflectionFactory rf = ReflectionFactory.getReflectionFactory();
			Constructor<?> objDef = parent.getDeclaredConstructor();
			Constructor<?> intConstr = rf.newConstructorForSerialization(clazz, objDef);
			return clazz.cast(intConstr.newInstance());
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new IllegalStateException("Cannot create object", e);
		}
	}

	public static void setFinalPrivateField(Object object, String fieldName, Object value) {

		try {
			Class<?> objClass = object.getClass();

			Field field = objClass.getDeclaredField(fieldName);

			boolean isAccessible = field.isAccessible();

			field.setAccessible(true);
			field.set(object, value);
			field.setAccessible(isAccessible);

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public static Object getFinalPrivateField(Object object, String fieldName) {

		try {
			Class<?> objClass = object.getClass();

			Field field = objClass.getDeclaredField(fieldName);

			boolean isAccessible = field.isAccessible();

			field.setAccessible(true);
			Object value = field.get(object);
			field.setAccessible(isAccessible);

			return value;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
}