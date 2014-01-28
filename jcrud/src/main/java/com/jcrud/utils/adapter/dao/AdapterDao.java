package com.jcrud.utils.adapter.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface AdapterDao<T> {

	long save(T object);

	void update(T object);

	void delete(T object);

	void deleteById(long id);

	T getById(long id);

	List<T> getElements(int offset, int limit);

	List<T> getElements(DetachedCriteria criteria, int offset, int limit);
}