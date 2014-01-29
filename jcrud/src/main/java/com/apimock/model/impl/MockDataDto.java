package com.apimock.model.impl;

import com.apimock.dao.MockDataEntity;
import com.apimock.model.MockData;
import com.apimock.model.MockRequest;
import com.apimock.model.MockResponse;
import com.jcrud.utils.adapter.Adapt;

@Adapt(to = MockDataEntity.class)
public class MockDataDto implements MockData {

	private final long id;

	private final MockRequest request;

	private final MockResponse response;

	public MockDataDto(long id, MockRequest request, MockResponse response) {
		this.id = id;
		this.request = request;
		this.response = response;
	}

	public MockDataDto(MockRequest request, MockResponse response) {
		this.id = 0L;
		this.request = request;
		this.response = response;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public MockRequest getRequest() {
		return request;
	}

	@Override
	public MockResponse getResponse() {
		return response;
	}

}
