package com.jcrud.utils.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.apimock.dao.MockDataEntity;
import com.apimock.model.MockData;
import com.apimock.model.impl.MockDataDto;
import com.apimock.model.impl.MockRequestDto;
import com.apimock.model.impl.MockResponseDto;
import com.jcrud.model.HttpMethod;

public final class AdaptUtil {

	@SuppressWarnings("unchecked")
	public static <T> T fromSource(Object object) {

		T adaptedObject = null;

		if (object != null) {

			Class<?> objectClass = object.getClass();
			Adapt adapt = objectClass.getAnnotation(Adapt.class);

			Class<?> targetClass = null;
			Class<? extends ClassAdapter> classAdapterClass = null;

			if (adapt != null) {
				targetClass = adapt.to();
				classAdapterClass = adapt.with();
			}

			if (classAdapterClass == null) {
				adaptedObject = (T) adaptTo(object, targetClass);
			} else {
				adaptedObject = (T) adaptTo(object, targetClass, classAdapterClass);
			}
		}

		return adaptedObject;
	}

	public static <T> T adaptTo(Object object, Class<T> targetClass) {

		return adaptTo(object, targetClass, DefaultClassAdapter.class);
	}

	private static <T> T adaptTo(Object object, Class<T> targetClass, Class<? extends ClassAdapter> classAdapterClass) {

		if (targetClass == null) {
			throw new IllegalStateException(String.format("There isn't a target class defined for class '%s'", object.getClass()));
		}

		ClassAdapter classAdapter = SilentObjectCreator.create(classAdapterClass);
		T adaptedObject = classAdapter.adaptTo(object, targetClass);

		return adaptedObject;
	}

	public static <T> T fromTarget(Object targetObject, Class<T> asClass) {

		T srcObject = null;

		if (targetObject != null) {

			Class<? extends ClassAdapter> classAdapterClass = null;

			Adapt adapt = asClass.getAnnotation(Adapt.class);
			if (adapt != null) {
				classAdapterClass = adapt.with();
			}

			if (classAdapterClass == null) {
				classAdapterClass = DefaultClassAdapter.class;
			}

			ClassAdapter classAdapter = SilentObjectCreator.create(classAdapterClass);
			srcObject = classAdapter.adaptFrom(targetObject, asClass);
		}
		return srcObject;
	}

	public static void main(String[] args) {

		MockRequestDto request = new MockRequestDto(HttpMethod.DELETE, "miPath", 8, null);
		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		list.add("mamaaa");
		list.add("otroooo");
		headers.put("helllooo", list);
		MockResponseDto response = new MockResponseDto(395, headers, null);

		MockDataDto mockDataDto = new MockDataDto(23L, request, response);
		MockDataEntity entity = AdaptUtil.fromSource(mockDataDto);

		System.out.println(entity);

		MockData mockData = AdaptUtil.fromTarget(entity, MockDataDto.class);
		Map<String, List<String>> headers2 = mockData.getResponse().getHeaders();

		List<String> list2 = headers2.get("helllooo");

		System.out.println(headers2);
		System.out.println(list2);
	}

}
