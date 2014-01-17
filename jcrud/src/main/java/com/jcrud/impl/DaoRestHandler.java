package com.jcrud.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jcrud.jpa.GenericDao;
import com.jcrud.model.RestHandler;
import com.jcrud.model.exceptions.CRUDResourceNotExistent;

public class DaoRestHandler<T> implements RestHandler<T> {

	@Autowired
	private GenericDao genericDao;

	private final Class<T> resourceClass;

	public DaoRestHandler(Class<T> resourceClass) {
		this.resourceClass = resourceClass;
	}

	@Override
	public Class<T> getResourceClass() {
		return resourceClass;
	}

	@Override
	public T handlePOST(T newObject) {
		String id = genericDao.save(newObject);
		// TODO:
		// newObject.setId(id);
		return newObject;
	}

	@Override
	public T handlePUT(String id, T objectUpdates) throws CRUDResourceNotExistent {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handleDELETE(String id) throws CRUDResourceNotExistent {
		// TODO Auto-generated method stub

	}

	@Override
	public T handleGET(String id) throws CRUDResourceNotExistent {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> handleGET(int elementsCount, int pageNumber) {
		// TODO Auto-generated method stub
		return null;
	}

}
