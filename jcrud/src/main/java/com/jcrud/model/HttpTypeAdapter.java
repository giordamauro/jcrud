package com.jcrud.model;


public interface HttpTypeAdapter {

	<T> T getFromRequest(HttpRequest request, Class<T> resourceClass);

	HttpResponse toHttpResponse(Object responseObject);
}
