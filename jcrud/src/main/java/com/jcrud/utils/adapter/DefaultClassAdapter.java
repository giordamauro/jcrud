package com.jcrud.utils.adapter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class DefaultClassAdapter implements ClassAdapter {

	private static final FieldAdapter defaultFieldAdapter = new DefaultFieldAdapter();

	@Override
	public <T> T adaptTo(Object srcObject, Class<T> targetClass) {

		T targetObject = null;
		if (srcObject != null) {

			targetObject = SilentObjectCreator.create(targetClass);

			Class<?> srcClass = srcObject.getClass();
			Field[] srcFields = srcClass.getDeclaredFields();

			for (Field srcField : srcFields) {

				if (!Modifier.isStatic(srcField.getModifiers())) {

					String srcFieldName = srcField.getName();
					Field targetField = null;

					Class<? extends FieldAdapter> fieldAdapterClass = null;

					AdaptField fieldAdapterAnn = srcField.getAnnotation(AdaptField.class);
					if (fieldAdapterAnn != null) {

						String targetFieldName = fieldAdapterAnn.toName();
						if (!targetFieldName.equals("")) {
							targetField = getDeclaredField(targetClass, targetFieldName);
						}

						fieldAdapterClass = fieldAdapterAnn.with();
					}

					if (targetField == null) {
						targetField = getDeclaredField(targetClass, srcFieldName);
					}

					if (fieldAdapterClass == null && srcField.getAnnotation(JsonAdapt.class) != null) {
						fieldAdapterClass = JsonFieldAdapter.class;
					}

					FieldAdapter fieldAdapter = defaultFieldAdapter;

					if (fieldAdapterClass != null) {
						fieldAdapter = SilentObjectCreator.create(fieldAdapterClass);
					}

					Object srcFieldValue = SilentObjectCreator.getFinalPrivateField(srcObject, srcField);
					fieldAdapter.adaptTo(srcField, srcFieldValue, targetField, targetObject);
				}
			}
		}
		return targetObject;
	}

	@Override
	public <T> T adaptFrom(Object targetObject, Class<T> srcClass) {

		T srcObject = null;

		if (targetObject != null) {

			Class<?> targetClass = targetObject.getClass();
			srcObject = SilentObjectCreator.create(srcClass);

			Field[] srcFields = srcClass.getDeclaredFields();

			for (Field srcField : srcFields) {

				if (!Modifier.isStatic(srcField.getModifiers())) {

					String srcFieldName = srcField.getName();
					Field targetField = null;

					Class<? extends FieldAdapter> fieldAdapterClass = null;

					AdaptField fieldAdapterAnn = srcField.getAnnotation(AdaptField.class);
					if (fieldAdapterAnn != null) {

						String targetFieldName = fieldAdapterAnn.toName();
						if (!targetFieldName.equals("")) {
							targetField = getDeclaredField(targetClass, targetFieldName);
						}

						fieldAdapterClass = fieldAdapterAnn.with();
					}

					if (targetField == null) {
						targetField = getDeclaredField(targetClass, srcFieldName);
					}

					if (targetField == null) {
						targetField = getDeclaredField(targetClass, srcFieldName);
					}

					if (fieldAdapterClass == null && srcField.getAnnotation(JsonAdapt.class) != null) {
						fieldAdapterClass = JsonFieldAdapter.class;
					}

					FieldAdapter fieldAdapter = defaultFieldAdapter;

					if (fieldAdapterClass != null) {
						fieldAdapter = SilentObjectCreator.create(fieldAdapterClass);
					}

					Object targetFieldValue = SilentObjectCreator.getFinalPrivateField(targetObject, targetField);

					fieldAdapter.adaptFrom(targetField, targetFieldValue, targetObject, srcField, srcObject);
				}
			}
		}
		return srcObject;
	}

	private Field getDeclaredField(Class<?> aClass, String fieldName) {
		try {

			return aClass.getDeclaredField(fieldName);

		} catch (Exception e) {
			throw new IllegalStateException(String.format("Field '%s' does not exist in class '%s'", fieldName, aClass), e);
		}
	}

}
