package com.jcrud.model.exceptions;

public class CRUDValidationException extends HttpResponseException {

	private static final long serialVersionUID = 5401677163340425070L;

	private final Class<?> resourceClass;

	private final String message;

	public CRUDValidationException(Class<?> resourceClass, String message) {
		this.resourceClass = resourceClass;
		this.message = message;
	}

	@Override
	public int getStatusCode() {
		return 422;
	}

	@Override
	public String getContentType() {
		return "text/plain";
	}

	@Override
	public String getMessage() {
		return String.format("Resource class '%s' - %s", resourceClass.getName(), message);
	}
}
