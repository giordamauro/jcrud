package com.jcrud.jpa;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;

public class GenericDaoImpl implements GenericDao {

	private final DaoSupportWrapper daoSupport;

	public GenericDaoImpl(SessionFactory sessionFactory) {
		this.daoSupport = new DaoSupportWrapper(sessionFactory);
	}

	public long save(Object object) {

		Serializable id = daoSupport.getHibernateTemplate().save(object);
		String strId = String.valueOf(id);

		return Long.valueOf(strId);
	}

	public void update(Object object) {
		daoSupport.getHibernateTemplate().update(object);
	}

	public void delete(Object object) {
		daoSupport.getHibernateTemplate().delete(object);
	}

	public void deleteById(Class<?> daoClass, long id) {

		Object object = getById(daoClass, id);
		delete(object);
	}

	public <T> T getById(Class<T> daoClass, long id) {
		T object = daoSupport.getHibernateTemplate().get(daoClass, id);

		return object;
	}

	public <T> List<T> getElements(DetachedCriteria criteria, int offset, int limit) {

		@SuppressWarnings("unchecked")
		List<T> elements = daoSupport.getHibernateTemplate().findByCriteria(criteria, offset, limit);

		return elements;
	}

	public <T> List<T> getElements(Class<T> daoClass, int offset, int limit) {

		DetachedCriteria criteria = DetachedCriteria.forClass(daoClass);
		List<T> elements = getElements(criteria, offset, limit);

		return elements;
	}
}
