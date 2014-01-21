package com.jcrud.model;

import java.util.List;

import com.jcrud.model.exceptions.CRUDResourceNotExistent;

public interface RestHandler {

	<T> T handlePOST(T newObject);

	<T> T handlePUT(long id, T objectUpdates) throws CRUDResourceNotExistent;

	void handleDELETE(Class<?> resourceClass, long id) throws CRUDResourceNotExistent;

	<T> T handleGET(Class<T> resourceClass, long id) throws CRUDResourceNotExistent;

	<T> List<T> handleGET(Class<T> resourceClass, int elementsCount, int pageNumber);

	<T> List<T> handleGET(Class<T> resourceClass, int elementsCount, int pageNumber, String query);
}
