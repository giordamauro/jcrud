package com.apimock.model.impl;

import com.apimock.dao.MockRequestEntity;
import com.apimock.model.MockRequest;
import com.apimock.model.MockRequestEvaluator;
import com.jcrud.model.HttpMethod;
import com.jcrud.model.HttpRequest;
import com.jcrud.utils.adapter.Adapt;

@Adapt(to = MockRequestEntity.class)
public class MockRequestDto implements MockRequest {

	private final HttpMethod method;

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
		
		boolean evaluation = true;
		if(evaluator != null){
			evaluation = evaluator.evaluate(this, request);
		}
		
		return evaluation;
	}
}
