package com.jcrud.impl.stub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcrud.model.RestHandler;
import com.jcrud.model.exceptions.CRUDResourceNotExistent;

public class StubRestHandler<T> implements RestHandler<T> {

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

		return values;
	}

}
