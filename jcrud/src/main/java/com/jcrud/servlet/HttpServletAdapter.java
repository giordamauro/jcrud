package com.jcrud.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jcrud.model.HttpRequest;
import com.jcrud.model.HttpResponse;

public interface HttpServletAdapter {

	HttpRequest adaptRequest(HttpServletRequest request);

	void adaptResponse(HttpResponse httpResponse, HttpServletResponse response);
}
