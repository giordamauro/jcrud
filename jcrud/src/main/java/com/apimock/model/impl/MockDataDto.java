package com.apimock.model.impl;

import com.apimock.dao.MockDataEntity;
import com.apimock.model.MockData;
import com.apimock.model.MockRequest;
import com.apimock.model.MockResponse;
import com.jcrud.utils.adapter.Adapt;

@Adapt(to = MockDataEntity.class)
public class MockDataDto implements MockData {

	private final String uuid;

	private final MockRequest request;

	private final MockResponse response;

	public MockDataDto(String uuid, MockRequest request, MockResponse response) {
		this.uuid = uuid;
		this.request = request;
		this.response = response;
	}

	@Override
	public String getUuid() {
		return uuid;
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
