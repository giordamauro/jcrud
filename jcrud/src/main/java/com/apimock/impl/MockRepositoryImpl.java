package com.apimock.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.apimock.dao.MockDataEntity;
import com.apimock.model.MockData;
import com.apimock.model.MockRepository;
import com.jcrud.jpa.GenericDao;
import com.jcrud.model.HttpMethod;

public class MockRepositoryImpl implements MockRepository {

	private static final int DEFAULT_LIMIT = 50;

	private final GenericDao genericDao;

	public MockRepositoryImpl(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	@Override
	public List<MockData> getMocksData(HttpMethod method, String path) {

		DetachedCriteria criteria = DetachedCriteria.forClass(MockDataEntity.class);
		DetachedCriteria criteria2 = criteria.createCriteria("request");
		criteria2.add(Restrictions.eq("method", method));
		criteria2.add(Restrictions.like("path", path));
		criteria2.addOrder(Order.desc("priority"));

		List<MockData> elements = genericDao.getElements(criteria, 0, DEFAULT_LIMIT);

		return elements;
	}

}
