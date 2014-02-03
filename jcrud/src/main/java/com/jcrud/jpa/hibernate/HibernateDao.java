package com.jcrud.jpa.hibernate;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface HibernateDao {

	<T> List<T> getElements(DetachedCriteria criteria, int offset, int limit);
}
