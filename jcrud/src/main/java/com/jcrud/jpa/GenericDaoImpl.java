package com.jcrud.jpa;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class GenericDaoImpl extends HibernateDaoSupport implements GenericDao {

	public long save(Object object) {

		Serializable id = getHibernateTemplate().save(object);
		String strId = String.valueOf(id);

		return Long.valueOf(strId);
	}

	public void update(Object object) {
		getHibernateTemplate().update(object);
	}

	public void delete(Object object) {
		getHibernateTemplate().delete(object);
	}

	public void deleteById(Class<?> daoClass, long id) {

		Object object = getById(daoClass, id);
		delete(object);
	}

	public <T> T getById(Class<T> daoClass, long id) {
		T object = getHibernateTemplate().get(daoClass, id);

		return object;
	}

	public <T> List<T> getElements(Class<T> daoClass, DetachedCriteria criteria, int offset, int limit) {

		@SuppressWarnings("unchecked")
		List<T> elements = getHibernateTemplate().findByCriteria(criteria, offset, limit);

		return elements;
	}

	public <T> List<T> getElements(Class<T> daoClass, int offset, int limit) {

		DetachedCriteria criteria = DetachedCriteria.forClass(daoClass);
		List<T> elements = getElements(daoClass, criteria, offset, limit);

		return elements;
	}
}
