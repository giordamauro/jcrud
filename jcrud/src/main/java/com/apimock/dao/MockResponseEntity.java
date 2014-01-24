package com.apimock.dao;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.apimock.model.MockResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Entity
@Table(name = "mockresponse")
public class MockResponseEntity implements MockResponse {

	private static final Type headersMapType = new TypeToken<Map<String, List<String>>>() {
	}.getType();

	private static final Gson gson = new Gson();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long id;

	@Column
	private int statusCode;

	@Column
	private String headers;

	@Column
	private byte[] content;

	public MockResponseEntity() {
	}

	public MockResponseEntity(int statusCode, Map<String, List<String>> headers, byte[] content) {
		setStatusCode(statusCode);
		setHeaders(headers);
		setContent(content);
	}

	@Override
	public int getStatusCode() {
		return statusCode;
	}

	@Override
	public Map<String, List<String>> getHeaders() {
		Map<String, List<String>> headersMap = gson.fromJson(headers, headersMapType);

		return headersMap;
	}

	@Override
	public byte[] getContent() {
		return content;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public void setHeaders(Map<String, List<String>> headers) {
		this.headers = gson.toJson(headers);
	}
}
