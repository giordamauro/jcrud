package com.jcrud.impl;

import com.jcrud.model.RestHandler;

public interface RestHandlerFactory {

	<T> RestHandler<T> newRestHandler(Class<T> resourceClass);
}
