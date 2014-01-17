package com.jcrud.impl.stub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcrud.model.RestHandler;
import com.jcrud.model.exceptions.CRUDResourceNotExistent;

public class StubRestHandler<T> implements RestHandler<T> {

	private static final int DEFAULT_ELEMENTS_COUNT = 20;
	private static final int DEFAULT_PAGE_NUMBER = 0;

	private final Map<String, T> resources = new HashMap<String, T>();

	private final Class<T> resourceClass;

	public StubRestHandler(Class<T> resourceClass) {
		this.resourceClass = resourceClass;
	}

	@Override
	public Class<T> getResourceClass() {
		return resourceClass;
	}

	@Override
	public T handlePOST(T newObject) {
		String id = String.valueOf(resources.size());
		Idable idable = (Idable) newObject;
		idable.setId(id);
		resources.put(id, newObject);
		return newObject;
	}

	@Override
	public T handlePUT(String id, T objectUpdates) throws CRUDResourceNotExistent {
		if (!resources.containsKey(id)) {
			throw new CRUDResourceNotExistent(resourceClass, id);
		}
		resources.put(id, objectUpdates);
		return objectUpdates;
	}

	@Override
	public void handleDELETE(String id) throws CRUDResourceNotExistent {
		if (!resources.containsKey(id)) {
			throw new CRUDResourceNotExistent(resourceClass, id);
		}
		resources.remove(id);
	}

	@Override
	public T handleGET(String id) throws CRUDResourceNotExistent {
		if (!resources.containsKey(id)) {
			throw new CRUDResourceNotExistent(resourceClass, id);
		}
		return resources.get(id);
	}

	@Override
	public List<T> handleGET(int elementsCount, int pageNumber) {
		List<T> values = new ArrayList<T>(resources.values());

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

		List<T> elements = null;
		if (fromIndex < toIndex) {
			elements = values.subList(fromIndex, toIndex);
		} else {
			elements = Collections.emptyList();
		}
		return elements;
	}
}
