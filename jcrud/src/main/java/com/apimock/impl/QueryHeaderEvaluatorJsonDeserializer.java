package com.apimock.impl;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.apimock.model.MockRequestEvaluator;
import com.apimock.model.impl.QueryHeaderEvaluator;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.jcrud.utils.json.CustomJsonDeserializer;

public class QueryHeaderEvaluatorJsonDeserializer implements CustomJsonDeserializer<MockRequestEvaluator> {

	@Override
	public MockRequestEvaluator deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

		JsonObject requestData = json.getAsJsonObject();
		JsonElement queryParams = requestData.get("queryParams");
		JsonElement headers = requestData.get("headers");

		Type mapType = new TypeToken<Map<String, List<String>>>() {
		}.getType();
		Map<String, List<String>> queryParamsMap = context.deserialize(queryParams, mapType);
		Map<String, List<String>> headersMap = context.deserialize(headers, mapType);

		QueryHeaderEvaluator customMatcher = new QueryHeaderEvaluator(queryParamsMap, headersMap);

		return customMatcher;
	}

	@Override
	public Class<MockRequestEvaluator> getDeserializingClass() {
		return MockRequestEvaluator.class;
	}

}
