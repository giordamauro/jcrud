package com.jcrud.model;

public interface CRUDDispatcher {

	HttpResponse handleRequest(HttpRequest request);

	void registerResource(String path, Class<?> resourceClass);
}
