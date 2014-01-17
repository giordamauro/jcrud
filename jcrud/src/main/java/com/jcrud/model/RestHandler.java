package com.jcrud.model;

import java.util.List;

import com.jcrud.model.exceptions.CRUDResourceNotExistent;

public interface RestHandler<T> {

	Class<T> getResourceClass();

	T handlePOST(T newObject);

	T handlePUT(String id, T objectUpdates) throws CRUDResourceNotExistent;

	void handleDELETE(String id) throws CRUDResourceNotExistent;

	T handleGET(String id) throws CRUDResourceNotExistent;

	List<T> handleGET(int elementsCount, int pageNumber);
}
