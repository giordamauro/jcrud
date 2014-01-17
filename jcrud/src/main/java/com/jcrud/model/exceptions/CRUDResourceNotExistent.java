package com.jcrud.model.exceptions;

public class CRUDResourceNotExistent extends Exception implements HttpResponseException {

	private static final long serialVersionUID = 5401677163340425070L;

	private final Class<?> resourceClass;

	private final String id;

	public CRUDResourceNotExistent(Class<?> resourceClass, String id) {
		this.resourceClass = resourceClass;
		this.id = id;
	}

	@Override
	public int getStatusCode() {
		return 404;
	}

	@Override
	public String getContentType() {
		return "text/plain";
	}

	@Override
	public String getMessage() {
		return String.format("Resource from class '%s' with id '%s' does not exist", resourceClass.getName(), id);
	}
}
