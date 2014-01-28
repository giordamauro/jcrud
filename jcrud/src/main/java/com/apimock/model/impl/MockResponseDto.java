package com.apimock.model.impl;

import java.util.List;
import java.util.Map;

import com.apimock.entityadapter.model.JsonTypeAdapter;
import com.apimock.entityadapter.model.AdapteField;
import com.apimock.model.MockResponse;

public class MockResponseDto implements MockResponse {

	private final int statusCode;

	@AdapteField(withClass = JsonTypeAdapter.class)
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
