package com.jcrud.impl.stub;

import java.util.HashMap;
import java.util.Map;

import com.jcrud.impl.RestHandlerFactory;
import com.jcrud.model.RestHandler;

public class StubRestHandlerFactory implements RestHandlerFactory {

	private static final Map<Class<?>, StubRestHandler<?>> restHandlers = new HashMap<Class<?>, StubRestHandler<?>>();

	@Override
	public <T> RestHandler<T> newRestHandler(Class<T> resourceClass) {

		@SuppressWarnings("unchecked")
		RestHandler<T> restHandler = (RestHandler<T>) restHandlers.get(resourceClass);

		if (restHandler == null) {
			restHandler = new StubRestHandler<T>(resourceClass);
			restHandlers.put(resourceClass, (StubRestHandler<?>) restHandler);
		}

		return restHandler;
	}

}
