package com.apimock.model.impl;

import com.apimock.entityadapter.model.AdapteField;
import com.apimock.model.MockRequest;
import com.apimock.model.MockRequestEvaluator;
import com.jcrud.model.HttpMethod;
import com.jcrud.model.HttpRequest;

public class MockRequestDto implements MockRequest {

	private final HttpMethod method;

	@AdapteField(toName = "pathing")
	private final String path;

	private final int priority;

	private final MockRequestEvaluator evaluator;

	public MockRequestDto(HttpMethod method, String path, int priority, MockRequestEvaluator evaluator) {
		this.method = method;
		this.path = path;
		this.priority = priority;
		this.evaluator = evaluator;
	}

	@Override
	public HttpMethod getMethod() {
		return method;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public boolean evaluate(HttpRequest request) {
		return evaluator.evaluate(this, request);
	}
}
