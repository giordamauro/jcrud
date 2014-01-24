package com.apimock.model.exceptions;

import com.jcrud.model.HttpRequest;

public class MockServiceNotFoundException extends Exception {

	private static final long serialVersionUID = -2495135857252042994L;

	private final HttpRequest request;

	public MockServiceNotFoundException(HttpRequest request) {
		this.request = request;
	}

	public HttpRequest getMockRequest() {
		return request;
	}

}
