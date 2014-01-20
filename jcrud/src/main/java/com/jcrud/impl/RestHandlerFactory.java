package com.jcrud.impl;

import com.jcrud.model.RestHandler;

public interface RestHandlerFactory {

	RestHandler getRestHandler(Class<?> resourceClass);
}
