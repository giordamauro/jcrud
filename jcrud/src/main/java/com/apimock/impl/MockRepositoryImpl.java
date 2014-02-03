package com.apimock.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.apimock.dao.MockDataEntity;
import com.apimock.model.MockData;
import com.apimock.model.MockRepository;
import com.apimock.model.impl.MockDataDto;
import com.jcrud.jpa.GenericDao;
import com.jcrud.jpa.hibernate.HibernateCriteria;
import com.jcrud.model.HttpMethod;

public class MockRepositoryImpl implements MockRepository {

	private static final int DEFAULT_LIMIT = 50;

	private final GenericDao genericDao;

	public MockRepositoryImpl(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	@Override
	public List<MockData> getMocksData(HttpMethod method, String path) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MockDataEntity.class);
		DetachedCriteria criteria2 = detachedCriteria.createCriteria("request");
		criteria2.add(Restrictions.eq("method", method));
		criteria2.add(Restrictions.like("path", path));
		criteria2.addOrder(Order.desc("priority"));

		HibernateCriteria<MockDataDto> criteria = new HibernateCriteria<MockDataDto>(detachedCriteria, MockDataDto.class);
		
		List<? extends MockData> elements = genericDao.getElements(criteria, 0, DEFAULT_LIMIT);

		@SuppressWarnings("unchecked")
		List<MockData> mockDataElements = (List<MockData>) elements;
		
		return mockDataElements;
	}

}
