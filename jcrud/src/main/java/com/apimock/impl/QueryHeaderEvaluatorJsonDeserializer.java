package com.apimock.impl;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.apimock.dao.MockRequestEvaluatorEntity;
import com.apimock.dao.evaluators.QueryHeaderEvaluatorEntity;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.jcrud.utils.json.CustomJsonDeserializer;

public class QueryHeaderEvaluatorJsonDeserializer implements CustomJsonDeserializer<MockRequestEvaluatorEntity> {

	@Override
	public MockRequestEvaluatorEntity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

		JsonObject requestData = json.getAsJsonObject();
		JsonElement queryParams = requestData.get("queryParams");
		JsonElement headers = requestData.get("headers");

		Type mapType = new TypeToken<Map<String, List<String>>>() {
		}.getType();
		Map<String, List<String>> queryParamsMap = context.deserialize(queryParams, mapType);
		Map<String, List<String>> headersMap = context.deserialize(headers, mapType);

		MockRequestEvaluatorEntity customMatcher = new QueryHeaderEvaluatorEntity(queryParamsMap, headersMap);

		return customMatcher;
	}

	@Override
	public Class<MockRequestEvaluatorEntity> getDeserializingClass() {
		return MockRequestEvaluatorEntity.class;
	}

}
