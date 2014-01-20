package com.jcrud.impl;

import java.util.List;

import com.jcrud.jpa.GenericDao;
import com.jcrud.model.RestHandler;
import com.jcrud.model.exceptions.CRUDResourceNotExistent;

public class DaoRestHandler implements RestHandler {

	private final GenericDao genericDao;

	public DaoRestHandler(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	@Override
	public <T> T handlePOST(T newObject) {

		@SuppressWarnings("unchecked")
		Class<T> resourceClass = (Class<T>) newObject.getClass();
		long id = genericDao.save(newObject);
		T object = genericDao.getById(resourceClass, id);

		return object;
	}

	@Override
	public <T> T handlePUT(long id, T objectUpdates) throws CRUDResourceNotExistent {
		@SuppressWarnings("unchecked")
		Class<T> resourceClass = (Class<T>) objectUpdates.getClass();

		genericDao.update(objectUpdates);
		T object = genericDao.getById(resourceClass, id);
		return object;
	}

	@Override
	public void handleDELETE(Class<?> resourceClass, long id) throws CRUDResourceNotExistent {
		Object object = genericDao.getById(resourceClass, id);
		if (object == null) {
			throw new CRUDResourceNotExistent(resourceClass, id);
		}
		genericDao.deleteById(resourceClass, id);
	}

	@Override
	public <T> T handleGET(Class<T> resourceClass, long id) throws CRUDResourceNotExistent {
		T object = genericDao.getById(resourceClass, id);
		return object;
	}

	@Override
	public <T> List<T> handleGET(Class<T> resourceClass, int elementsCount, int pageNumber) {

		int offset = elementsCount * pageNumber;
		List<T> elements = genericDao.getElements(resourceClass, offset, elementsCount);
		return elements;
	}

}
