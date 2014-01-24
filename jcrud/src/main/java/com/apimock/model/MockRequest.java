package com.apimock.model;

import com.jcrud.model.HttpMethod;
import com.jcrud.model.HttpRequest;

public interface MockRequest {

	HttpMethod getMethod();

	String getPath();

	boolean evaluate(HttpRequest request);

	int getPriority();

}
