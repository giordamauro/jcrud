package com.apimock.model;

import java.io.Serializable;

import com.jcrud.model.HttpRequest;

public interface MockRequestEvaluator extends Serializable {

	boolean evaluate(MockRequest mockRequest, HttpRequest request);
}
