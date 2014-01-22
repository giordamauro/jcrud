package com.jcrud.model.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcrud.model.HttpMethod;
import com.jcrud.model.HttpRequest;

public class HttpRequestImpl implements HttpRequest {

	private final HttpMethod method;

	private final String path;

	private final Map<String, List<String>> headers = new HashMap<String, List<String>>();

	private final Map<String, List<String>> queryParams = new HashMap<String, List<String>>();

	private final InputStream content;

	public HttpRequestImpl(String method, String path, InputStream content) {
		this.method = HttpMethod.valueOf(method);
		this.path = path;
		this.content = content;
	}

	public HttpRequestImpl(String method, String path) {
		this(method, path, null);
	}

	@Override
	public HttpMethod getMethod() {
		return method;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public Map<String, List<String>> getHeaders() {
		return Collections.unmodifiableMap(headers);
	}

	@Override
	public List<String> getHeaderValues(String name) {
		return headers.get(name);
	}

	@Override
	public Map<String, List<String>> getQueryParams() {
		return Collections.unmodifiableMap(queryParams);
	}

	@Override
	public List<String> getQueryParamValues(String name) {
		return queryParams.get(name);
	}

	@Override
	public InputStream getContent() {
		return content;
	}

	public void setHeader(String name, String value) {
		if (name == null || value == null) {
			throw new IllegalArgumentException("Name and value cannot be null");
		}
		List<String> values = headers.get(name);
		if (values == null) {
			values = new ArrayList<String>();
			headers.put(name, values);
		}
		values.add(value);
	}

	public void setHeaders(Map<String, List<String>> headers) {
		if (headers == null) {
			throw new IllegalArgumentException("Headers cannot be null");
		}
		this.headers.putAll(headers);
	}

	public void addQueryParam(String name, String value) {
		if (name == null || value == null) {
			throw new IllegalArgumentException("Name and value cannot be null");
		}
		List<String> values = queryParams.get(name);
		if (values == null) {
			values = new ArrayList<String>();
			queryParams.put(name, values);
		}
		values.add(value);
	}

	public void addQueryParams(Map<String, List<String>> params) {
		if (params == null) {
			throw new IllegalArgumentException("Params cannot be null");
		}
		queryParams.putAll(params);
	}

	@Override
	public String getQueryParam(String name) {

		List<String> queryValues = getQueryParamValues(name);
		if (queryValues == null) {
			return null;
		}
		if (queryValues.size() == 1) {
			return queryValues.get(0);
		}
		throw new IllegalStateException(String.format("Query param '%s' is not single valued"));
	}

	@Override
	public String getHeader(String name) {

		List<String> headerValues = getHeaderValues(name);
		if (headerValues == null) {
			return null;
		}
		if (headerValues.size() == 1) {
			return headerValues.get(0);
		}
		throw new IllegalStateException(String.format("Header '%s' is not single valued"));
	}
}
