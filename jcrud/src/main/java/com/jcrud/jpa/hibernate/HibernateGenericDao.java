package com.jcrud.jpa.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;

import com.jcrud.jpa.DaoCriteria;
import com.jcrud.jpa.DaoSupportWrapper;
import com.jcrud.jpa.GenericDao;

public class HibernateGenericDao implements GenericDao, HibernateDao {

	private final DaoSupportWrapper daoSupport;

	public HibernateGenericDao(SessionFactory sessionFactory) {
		this.daoSupport = new DaoSupportWrapper(sessionFactory);
	}

	public long save(Object object) {

		Serializable id = daoSupport.getHibernateTemplate().save(object);
		String strId = String.valueOf(id);

		return Long.valueOf(strId);
	}

	public void update(Object object) {
		daoSupport.getHibernateTemplate().merge(object);
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

	public <T> List<T> getElements(Class<T> daoClass, int offset, int limit) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(daoClass);
		DaoCriteria<T> criteria = new HibernateCriteria<T>(detachedCriteria, daoClass);
		
		List<T> elements = getElements(criteria, offset, limit);

		return elements;
	}

	@Override
	public <T> List<T> getElements(DaoCriteria<T> criteria, int offset, int limit) {
		
		List<T> elements = criteria.getElements(this, offset, limit);

		return elements;
	}
	
	public <T> List<T> getElements(DetachedCriteria criteria, int offset, int limit){
		
		@SuppressWarnings("unchecked")
		List<T> elements = daoSupport.getHibernateTemplate().findByCriteria(criteria, offset, limit);
		
		return elements;
	}
}
