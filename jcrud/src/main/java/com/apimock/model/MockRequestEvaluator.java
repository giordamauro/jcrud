package com.apimock.model;

import com.jcrud.model.HttpRequest;

public interface MockRequestEvaluator {

	boolean evaluate(MockRequest mockRequest, HttpRequest request);
}
