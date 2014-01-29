package com.jcrud.utils.adapter.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.jcrud.jpa.GenericDao;
import com.jcrud.utils.adapter.Adapt;
import com.jcrud.utils.adapter.AdaptUtil;

public class GenericAdapterDao implements GenericDao {

	private final GenericDao genericDao;

	public GenericAdapterDao(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	@Override
	public long save(Object object) {

		Class<?> objectClass = object.getClass();

		if (isAdaptAnnotatedClass(objectClass)) {
			object = AdaptUtil.fromSource(object);
		}

		long id = genericDao.save(object);

		return id;
	}

	@Override
	public void update(Object object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Object object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Class<?> daoClass, long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> T getById(Class<T> daoClass, long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> getElements(Class<T> daoClass, int offset, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<T> getElements(DetachedCriteria criteria, int offset, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean isAdaptAnnotatedClass(Class<?> aClass) {

		Adapt adapt = aClass.getAnnotation(Adapt.class);
		boolean isAdaptAnnotated = adapt != null;

		return isAdaptAnnotated;
	}
}
