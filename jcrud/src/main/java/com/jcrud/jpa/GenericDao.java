package com.jcrud.jpa;

import java.util.List;

public interface GenericDao {

	String save(Object object);

	void update(Object object);

	void delete(Object object);

	void deleteById(Class<?> daoClass, String id);

	<T> T getById(Class<T> daoClass, String id);

	<T> List<T> getElements(Class<T> daoClass, int offset, int limit);
}