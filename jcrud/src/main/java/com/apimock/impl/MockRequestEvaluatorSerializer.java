package com.apimock.impl;

import java.lang.reflect.Type;

import com.apimock.model.MockRequestEvaluator;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.jcrud.utils.json.CustomJsonSerializer;

public class MockRequestEvaluatorSerializer implements CustomJsonSerializer<MockRequestEvaluator>{

	@Override
	public JsonElement serialize(MockRequestEvaluator src, Type typeOfSrc, JsonSerializationContext context) {

		JsonElement element = context.serialize(src);
	
		return element;
	}

	@Override
	public Class<MockRequestEvaluator> getSerializingClass() {
	
		return MockRequestEvaluator.class;
	}

}
