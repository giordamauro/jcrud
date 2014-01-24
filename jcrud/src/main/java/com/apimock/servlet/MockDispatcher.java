package com.apimock.servlet;

import com.jcrud.model.HttpRequest;
import com.jcrud.model.HttpResponse;

public interface MockDispatcher {

	HttpResponse handleRequest(HttpRequest request);

}
