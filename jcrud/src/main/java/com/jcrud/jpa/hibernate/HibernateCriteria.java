package com.jcrud.jpa.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.jcrud.jpa.DaoCriteria;
import com.jcrud.jpa.GenericDao;

public class HibernateCriteria<T> implements DaoCriteria<T> {
	
	private final DetachedCriteria criteria;
	
	private final Class<T> elementClass;
	
	public HibernateCriteria(DetachedCriteria criteria, Class<T> elementClass) {
		this.criteria = criteria;
		this.elementClass = elementClass;
	}

	@Override
	public List<T> getElements(GenericDao genericDao, int offset, int limit) {
		
		HibernateDao hibernateDao  = (HibernateDao) genericDao;
		
		List<T> elements =  hibernateDao.getElements(criteria, offset, limit);
		
		return elements;
	}

	@Override
	public Class<T> getResourceClass() {
		return elementClass;
	}


}
