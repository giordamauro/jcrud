package com.jcrud.model.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.jcrud.model.HttpResponse;

public class HttpResponseImpl implements HttpResponse {

	private final int status;

	private final Map<String, String> headers = new HashMap<String, String>();

	private final InputStream content;

	public HttpResponseImpl(int status, InputStream content) {
		this.status = status;
		this.content = content;
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

	public void setHeader(String name, String value) {
		headers.put(name, value);
	}

}
