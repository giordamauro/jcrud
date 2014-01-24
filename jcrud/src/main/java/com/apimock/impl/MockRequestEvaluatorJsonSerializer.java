package com.apimock.impl;

import java.lang.reflect.Type;

import com.apimock.dao.MockRequestEvaluatorEntity;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.jcrud.utils.json.CustomJsonSerializer;

public class MockRequestEvaluatorJsonSerializer implements CustomJsonSerializer<MockRequestEvaluatorEntity> {

	@Override
	public JsonElement serialize(MockRequestEvaluatorEntity src, Type typeOfSrc, JsonSerializationContext context) {

		Class<?> srcType = src.getClass();
		JsonElement jsonElement = context.serialize(src, srcType);

		return jsonElement;
	}

	@Override
	public Class<MockRequestEvaluatorEntity> getSerializingClass() {
		return MockRequestEvaluatorEntity.class;
	}

}
