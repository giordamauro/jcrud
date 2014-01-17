package com.jcrud.model.exceptions;

public interface HttpResponseException {

	int getStatusCode();

	String getMessage();

	String getContentType();
}
