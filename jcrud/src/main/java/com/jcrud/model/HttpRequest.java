package com.jcrud.model;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface HttpRequest {

	HttpMethod getMethod();

	String getPath();

	Map<String, List<String>> getHeaders();

	String getHeader(String name);

	List<String> getHeaderValues(String name);

	Map<String, List<String>> getQueryParams();

	List<String> getQueryParamValues(String name);

	String getQueryParam(String name);

	InputStream getContent();
}
