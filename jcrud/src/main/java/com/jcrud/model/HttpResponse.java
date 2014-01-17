package com.jcrud.model;

import java.io.InputStream;
import java.util.Map;

public interface HttpResponse {

	int getStatus();

	Map<String, String> getHeaders();

	InputStream getContent();
}
