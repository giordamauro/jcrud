package com.apimock.model;

import java.util.List;
import java.util.Map;

public interface MockResponse {

	int getStatusCode();

	Map<String, List<String>> getHeaders();

	byte[] getContent();
}
