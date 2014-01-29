package com.jcrud.utils.adapter;

import java.lang.reflect.Field;

public class DefaultFieldAdapter implements FieldAdapter {

	private static final Class<?>[] SIMPLE_CLASSES = new Class<?>[] { String.class, Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class };

	@Override
	public void adaptTo(Field srcField, Object srcFieldValue, Field targetField, Object targetObject) {

		Class<?> srcFieldClass = srcField.getType();

		Object targetValue = null;

		if (isSimpleType(srcFieldClass)) {

			targetValue = srcFieldValue;
		} else {

			targetValue = AdaptUtil.fromSource(srcFieldValue);
		}

		SilentObjectCreator.setFinalPrivateField(targetObject, targetField, targetValue);
	}

	@Override
	public void adaptFrom(Field targetField, Object targetFieldValue, Object targetObject, Field srcField, Object srcObject) {

		if (targetFieldValue != null) {

			Class<?> srcFieldClass = srcField.getType();

			Object srcValue = null;

			if (isSimpleType(srcFieldClass)) {

				srcValue = targetFieldValue;
			} else {

				srcValue = AdaptUtil.fromTarget(targetFieldValue, srcFieldClass);
			}

			SilentObjectCreator.setFinalPrivateField(srcObject, srcField, srcValue);
		}
	}

	private boolean isSimpleType(Class<?> aClass) {

		boolean isSimpleType = false;

		if (aClass.isPrimitive() || aClass.isEnum() || aClass.isArray()) {
			isSimpleType = true;
		} else {
			int i = 0;
			while (!isSimpleType && i < SIMPLE_CLASSES.length) {
				isSimpleType = aClass.equals(SIMPLE_CLASSES[i]);
				i++;
			}
		}
		return isSimpleType;
	}

}
