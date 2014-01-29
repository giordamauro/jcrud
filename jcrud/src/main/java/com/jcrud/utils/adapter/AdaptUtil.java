package com.jcrud.utils.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.apimock.dao.MockDataEntity;
import com.apimock.model.MockData;
import com.apimock.model.MockRequestEvaluator;
import com.apimock.model.impl.CompositeEvaluator;
import com.apimock.model.impl.MockDataDto;
import com.apimock.model.impl.MockRequestDto;
import com.apimock.model.impl.MockResponseDto;
import com.apimock.model.impl.QueryHeaderEvaluator;
import com.apimock.model.impl.SerializedEvaluator;
import com.jcrud.model.HttpMethod;

public final class AdaptUtil {

	private static final Reflections reflections = new Reflections();

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

			Class<?> srcClass = asClass;

			if (asClass.isInterface()) {
				Class<?> targetClass = targetObject.getClass();
				srcClass = getAsClass(asClass, targetClass);
			}

			Class<? extends ClassAdapter> classAdapterClass = null;

			Adapt adapt = asClass.getAnnotation(Adapt.class);
			if (adapt != null) {
				classAdapterClass = adapt.with();
			}

			if (classAdapterClass == null) {
				classAdapterClass = DefaultClassAdapter.class;
			}

			ClassAdapter classAdapter = SilentObjectCreator.create(classAdapterClass);

			@SuppressWarnings("unchecked")
			T adaptedResult = (T) classAdapter.adaptFrom(targetObject, srcClass);

			srcObject = adaptedResult;
		}
		return srcObject;
	}

	private static <T> Class<?> getAsClass(Class<T> asClass, Class<?> targetClass) {

		Set<Class<? extends T>> implementations = reflections.getSubTypesOf(asClass);

		for (Class<?> implementingClass : implementations) {
			Adapt adaptAnn = implementingClass.getAnnotation(Adapt.class);
			if (adaptAnn != null) {

				Class<?> toClass = adaptAnn.to();
				if (toClass.equals(targetClass)) {
					return implementingClass;
				}
			}
		}
		throw new IllegalStateException(String.format("Couldn't find an implementation for interface class '%s' that matches with @Adapt(to = '%s')", asClass, targetClass));
	}

	public static void main(String[] args) throws Exception {

		long start = new Date().getTime();

		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		list.add("mamaaa");
		list.add("otroooo");
		headers.put("helllooo", list);

		QueryHeaderEvaluator evaluator = new QueryHeaderEvaluator(headers, headers);
		SerializedEvaluator evaluator2 = new SerializedEvaluator(evaluator);

		MockRequestEvaluator compositeEvaluator = new CompositeEvaluator(evaluator, evaluator2);

		MockRequestDto request = new MockRequestDto(HttpMethod.DELETE, "miPath", 8, compositeEvaluator);

		MockResponseDto response = new MockResponseDto(395, headers, null);

		MockDataDto mockDataDto = new MockDataDto(23L, request, response);

		MockDataEntity entity = AdaptUtil.fromSource(mockDataDto);

		// System.out.println(entity);
		//
		// @SuppressWarnings("resource")
		// ApplicationContext appContext = new
		// ClassPathXmlApplicationContext("context/applicationContext.xml");
		// GenericDao dao = appContext.getBean(GenericDao.class);
		//
		// dao.save(entity);

		MockData mockData = AdaptUtil.fromTarget(entity, MockDataDto.class);
		Map<String, List<String>> headers2 = mockData.getResponse().getHeaders();

		List<String> list2 = headers2.get("helllooo");

		System.out.println(headers2);
		System.out.println(list2);

		long end = new Date().getTime();

		System.out.println("Tiempo total: " + (end - start));
	}
}
