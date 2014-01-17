package com.jcrud.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;

import com.jcrud.model.HttpResponse;
import com.jcrud.model.exceptions.HttpResponseException;

public class ExceptionResponse implements HttpResponse {

	private final int status;

	private final Map<String, String> headers = Collections.singletonMap("Content-Type", "text/plain");

	private final InputStream content;

	private ExceptionResponse(int status, InputStream content) {
		this.status = status;
		this.content = content;
	}

	private ExceptionResponse(int status, String message) {
		this.status = status;

		byte[] bytes = message.getBytes();
		this.content = new ByteArrayInputStream(bytes);
	}

	@Override
	public int getStatus() {
		return status;
	}

	@Override
	public Map<String, String> getHeaders() {
		return headers;
	}

	@Override
	public InputStream getContent() {
		return content;
	}

	public static ExceptionResponse new404(String message) {
		ExceptionResponse response = new ExceptionResponse(404, message);
		return response;
	}

	public static ExceptionResponse new400(String message) {
		return new ExceptionResponse(400, message);
	}

	public static ExceptionResponse fromException(HttpResponseException responseException) {
		ExceptionResponse response = new ExceptionResponse(responseException.getStatusCode(), responseException.getMessage());
		return response;
	}
}
