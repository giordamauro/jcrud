package com.jcrud.model.exceptions;

public abstract class HttpResponseException extends Throwable{

	private static final long serialVersionUID = -8346575501861644000L;

	public abstract int getStatusCode();

	public abstract String getMessage();

	public abstract String getContentType();
}
