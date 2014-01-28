package com.apimock.model.impl;

import com.apimock.dao.MockDataEntity;
import com.apimock.dao.MockRequestEntity;
import com.apimock.dao.MockResponseEntity;
import com.apimock.entityadapter.model.Adapt;
import com.apimock.model.MockData;
import com.apimock.model.MockRequest;
import com.apimock.model.MockResponse;

@Adapt(to = MockDataEntity.class)
public class MockDataDto implements MockData {

	private final long id;

	@Adapt(from = MockRequestDto.class, to = MockRequestEntity.class)
	private final MockRequest request;

	@Adapt(from = MockResponseDto.class, to = MockResponseEntity.class)
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
