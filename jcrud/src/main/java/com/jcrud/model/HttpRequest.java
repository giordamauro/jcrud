package com.jcrud.model;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface HttpRequest {

	HttpMethod getMethod();

	String getPath();

	Map<String, List<String>> getHeaders();

	List<String> getHeader(String name);

	Map<String, List<String>> getQueryParams();

	List<String> getQueryParam(String name);

	InputStream getContent();
}
