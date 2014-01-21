package com.jcrud.impl.stub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcrud.model.RestHandler;
import com.jcrud.model.exceptions.CRUDResourceNotExistent;

public class HashedRestHandler implements RestHandler {

	private static final int DEFAULT_ELEMENTS_COUNT = 20;
	private static final int DEFAULT_PAGE_NUMBER = 0;

	private final Map<Long, Object> resources = new HashMap<Long, Object>();

	@Override
	public <T> T handlePOST(T newObject) {
		long id = Long.valueOf(resources.size());
		Idable idable = (Idable) newObject;
		idable.setId(id);
		resources.put(id, idable);
		return newObject;
	}

	@Override
	public <T> T handlePUT(long id, T objectUpdates) throws CRUDResourceNotExistent {
		if (!resources.containsKey(id)) {
			@SuppressWarnings("unchecked")
			Class<T> resourceClass = (Class<T>) objectUpdates.getClass();
			throw new CRUDResourceNotExistent(resourceClass, id);
		}
		resources.put(id, objectUpdates);
		return objectUpdates;
	}

	@Override
	public void handleDELETE(Class<?> resourceClass, long id) throws CRUDResourceNotExistent {
		if (!resources.containsKey(id)) {
			throw new CRUDResourceNotExistent(resourceClass, id);
		}
		resources.remove(id);
	}

	@Override
	public <T> T handleGET(Class<T> resourceClass, long id) throws CRUDResourceNotExistent {
		if (!resources.containsKey(id)) {
			throw new CRUDResourceNotExistent(resourceClass, id);
		}
		@SuppressWarnings("unchecked")
		T resource = (T) resources.get(id);

		return resource;
	}

	@Override
	public <T> List<T> handleGET(Class<T> resourceClass, int elementsCount, int pageNumber) {
		List<?> values = new ArrayList<Object>(resources.values());

		if (elementsCount == -1) {
			elementsCount = DEFAULT_ELEMENTS_COUNT;
		}

		if (pageNumber == -1) {
			pageNumber = DEFAULT_PAGE_NUMBER;
		}

		int fromIndex = pageNumber * elementsCount;
		int toIndex = fromIndex + elementsCount;

		if (toIndex > values.size()) {
			toIndex = values.size();
		}

		List<?> elements = null;
		if (fromIndex < toIndex) {
			elements = values.subList(fromIndex, toIndex);
		} else {
			elements = Collections.emptyList();
		}
		@SuppressWarnings("unchecked")
		List<T> elementsList = (List<T>) elements;

		return elementsList;
	}

	@Override
	public <T> List<T> handleGET(Class<T> resourceClass, int elementsCount, int pageNumber, String query) {
		throw new UnsupportedOperationException();
	}
}
