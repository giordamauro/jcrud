package com.apimock.model.impl;

import java.util.List;
import java.util.Map;

import com.apimock.dao.MockResponseEntity;
import com.apimock.model.MockResponse;
import com.jcrud.utils.adapter.Adapt;
import com.jcrud.utils.adapter.JsonAdapt;

@Adapt(to = MockResponseEntity.class)
public class MockResponseDto implements MockResponse {

	private final int statusCode;

	@JsonAdapt
	private final Map<String, List<String>> headers;

	private final byte[] content;

	public MockResponseDto(int statusCode, Map<String, List<String>> headers, byte[] content) {
		this.statusCode = statusCode;
		this.headers = headers;
		this.content = content;
	}

	@Override
	public int getStatusCode() {
		return statusCode;
	}

	@Override
	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	@Override
	public byte[] getContent() {
		return content;
	}

}
