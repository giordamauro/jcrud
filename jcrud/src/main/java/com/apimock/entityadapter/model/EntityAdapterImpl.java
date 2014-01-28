package com.apimock.entityadapter.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Entity;

import com.apimock.dao.MockDataEntity;
import com.apimock.model.MockData;
import com.apimock.model.impl.MockDataDto;
import com.apimock.model.impl.MockRequestDto;
import com.apimock.model.impl.MockResponseDto;
import com.jcrud.model.HttpMethod;

public class EntityAdapterImpl implements EntityAdapter {

	private static final Class<?>[] simpleClasses = new Class<?>[] { String.class, Boolean.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class };

	private final Map<Class<?>, Class<?>> registeredClasses = new HashMap<Class<?>, Class<?>>();

	public void registerClassEntity(Class<?> richObjectClass, Class<?> entityClass) {
		if (!isEntityClass(entityClass)) {
			throw new IllegalArgumentException(String.format("Class '%s' must be an Entity", entityClass));
		}

		registeredClasses.put(richObjectClass, entityClass);
	}

	@Override
	public <T> T toEntity(Object richObject) {

		Class<?> richObjectClass = richObject.getClass();
		if (isEntityClass(richObjectClass)) {

			@SuppressWarnings("unchecked")
			T entity = (T) richObject;

			return entity;
		}
		Class<?> entityClass = registeredClasses.get(richObjectClass);
		if (entityClass == null) {
			Adapt adapt = richObjectClass.getAnnotation(Adapt.class);
			if (adapt != null) {
				entityClass = adapt.to();
				registeredClasses.put(richObjectClass, entityClass);
			}
		}

		if (entityClass == null) {
			throw new IllegalStateException(String.format("Class '%s' is not registered to be adapted", richObjectClass));
		}

		@SuppressWarnings("unchecked")
		T entity = (T) getEntity(richObject, entityClass);

		return entity;
	}

	@Override
	public <T> T fromEntity(Object entityObject) {

		Class<?> entityClass = entityObject.getClass();

		if (!registeredClasses.containsValue(entityClass)) {
			throw new IllegalStateException(String.format("Entity class '%s' not registered to be adapted", entityClass));
		}

		Class<?> richObjectClass = findClassForEntity(entityClass);

		@SuppressWarnings("unchecked")
		T dto = (T) getFromEntity(entityObject, richObjectClass);

		return dto;
	}

	private Object getFromEntity(Object entityObject, Class<?> richObjectClass) {

		Field[] fields = richObjectClass.getDeclaredFields();
		Object richObject = SilentObjectCreator.create(richObjectClass);

		for (Field field : fields) {

			String fieldName = field.getName();
			String entityFieldName = fieldName;

			Class<? extends ValueTypeAdapter> typeAdapterClass = null;

			AdapteField typeAdapter = field.getAnnotation(AdapteField.class);
			if (typeAdapter != null) {

				String adapterFieldName = typeAdapter.toName();
				if (!adapterFieldName.equals("")) {
					entityFieldName = adapterFieldName;
				}

				typeAdapterClass = typeAdapter.withClass();
				if (typeAdapterClass.equals(VoidTypeAdapter.class)) {
					typeAdapterClass = null;
				}
			}

			Object value = SilentObjectCreator.getFinalPrivateField(entityObject, entityFieldName);

			if (typeAdapterClass != null) {

				ValueTypeAdapter adapter = SilentObjectCreator.create(typeAdapterClass);
				Class<?> valueFieldClass = getClassFieldType(richObjectClass, fieldName);

				Object objValue = adapter.valueFromEntity(value, valueFieldClass);
				SilentObjectCreator.setFinalPrivateField(richObject, fieldName, objValue);
			} else {

				if (value != null) {

					Object entityValue = getRichObjectValue(richObject, fieldName, value);
					SilentObjectCreator.setFinalPrivateField(richObject, fieldName, entityValue);
				}
			}
		}

		return richObject;
	}

	private Object getRichObjectValue(Object richObject, String fieldName, Object value) {
		try {
			Class<?> valueClass = value.getClass();
			if (isObjectType(valueClass)) {

				if (!registeredClasses.containsValue(valueClass)) {

					Class<?> richObjectClass = richObject.getClass();
					Class<?> richObjectFieldClass = getClassFieldType(richObjectClass, fieldName);

					if (isObjectType(richObjectFieldClass)) {
						value = getFromEntity(value, richObjectFieldClass);
					}
				} else {
					value = this.fromEntity(value);
				}
			}
			return value;

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	private Class<?> findClassForEntity(Class<?> entityClass) {

		for (Entry<Class<?>, Class<?>> entry : registeredClasses.entrySet()) {
			if (entry.getValue().equals(entityClass)) {
				return entry.getKey();
			}
		}

		return null;
	}

	private Object getEntity(Object richObject, Class<?> entityClass) {

		Class<?> richObjectClass = richObject.getClass();

		Field[] fields = richObjectClass.getDeclaredFields();
		Object entityObject = SilentObjectCreator.create(entityClass);

		for (Field field : fields) {

			String fieldName = field.getName();
			Object value = SilentObjectCreator.getFinalPrivateField(richObject, fieldName);
			String entityFieldName = fieldName;

			AdapteField typeAdapter = field.getAnnotation(AdapteField.class);
			if (typeAdapter != null) {

				String adapterFieldName = typeAdapter.toName();
				if (!adapterFieldName.equals("")) {
					entityFieldName = adapterFieldName;
				}

				Class<? extends ValueTypeAdapter> typeAdapterClass = typeAdapter.withClass();
				ValueTypeAdapter adapter = SilentObjectCreator.create(typeAdapterClass);

				Class<?> entityFieldClass = getClassFieldType(entityClass, entityFieldName);

				Object entityValue = adapter.valueToEntity(value, entityFieldClass);
				SilentObjectCreator.setFinalPrivateField(entityObject, entityFieldName, entityValue);
			} else {

				if (value != null) {

					Object entityValue = getEntityValue(entityObject, fieldName, value);
					SilentObjectCreator.setFinalPrivateField(entityObject, fieldName, entityValue);
				}
			}
		}

		return entityObject;
	}

	private Object getEntityValue(Object entityObject, String fieldName, Object value) {

		try {
			Class<?> valueClass = value.getClass();
			if (isObjectType(valueClass)) {

				if (!registeredClasses.containsKey(valueClass)) {

					Class<?> entityClass = entityObject.getClass();
					Class<?> entityFieldClass = getClassFieldType(entityClass, fieldName);

					if (isObjectType(entityFieldClass)) {
						value = getEntity(value, entityFieldClass);
					}
				} else {
					value = this.toEntity(value);
				}
			}
			return value;

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	private Class<?> getClassFieldType(Class<?> objectClass, String fieldName) {

		try {
			Field entityField = objectClass.getDeclaredField(fieldName);
			Class<?> entityFieldClass = null;

			if (entityField != null) {
				entityFieldClass = entityField.getType();
			}

			return entityFieldClass;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}

	}

	private boolean isEntityClass(Class<?> objectClass) {
		Entity entityAnnotation = objectClass.getAnnotation(Entity.class);
		boolean isEntity = (entityAnnotation != null);

		return isEntity;
	}

	private boolean isObjectType(Class<?> aClass) {

		boolean isObjectType = true;

		// TODO: deberia hacerse algo especial con el tipo Array
		if (aClass.isPrimitive() || aClass.isInterface() || aClass.isEnum() || aClass.isArray()) {
			isObjectType = false;
		} else {
			int i = 0;
			while (isObjectType && i < simpleClasses.length) {
				isObjectType = !aClass.equals(simpleClasses[i]);
				i++;
			}
		}
		return isObjectType;
	}

	public static void main(String[] args) {

		EntityAdapter adapter = new EntityAdapterImpl();
		// adapter.registerClassEntity(MockDataDto.class, MockDataEntity.class);
		// adapter.registerClassEntity(MockRequestDto.class,
		// MockRequestEntity.class);
		// adapter.registerClassEntity(MockResponseDto.class,
		// MockResponseEntity.class);

		MockRequestDto request = new MockRequestDto(HttpMethod.DELETE, "miPath", 8, null);
		Map<String, List<String>> headers = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		list.add("mamaaa");
		list.add("otroooo");
		headers.put("helllooo", list);
		MockResponseDto response = new MockResponseDto(395, headers, null);

		MockDataDto mockDataDto = new MockDataDto(23L, request, response);
		MockDataEntity entity = adapter.toEntity(mockDataDto);

		MockData mockData = adapter.fromEntity(entity);
		Map<String, List<String>> headers2 = mockData.getResponse().getHeaders();

		List<String> list2 = headers2.get("helllooo");

		System.out.println(headers2);
		System.out.println(list2);
	}

	@Override
	public void registerByAnnotations(Class<?> richObjectClass) {
		Adapt adapt = richObjectClass.getAnnotation(Adapt.class);
		if (adapt != null) {
			Class<?> entityClass = adapt.to();
			registeredClasses.put(richObjectClass, entityClass);
		}
	}
}
