package com.jcrud.utils.adapter.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.apimock.model.impl.MockDataDto;
import com.jcrud.jpa.GenericDao;
import com.jcrud.utils.adapter.Adapt;
import com.jcrud.utils.adapter.AdaptUtil;

public class MockDataAdapterDao implements AdapterDao<MockDataDto> {

	private final GenericDao genericDao;

	public MockDataAdapterDao(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	@Override
	public long save(MockDataDto mockData) {

		Object mockDataEntity = AdaptUtil.fromSource(mockData);
		long id = genericDao.save(mockDataEntity);

		return id;
	}

	@Override
	public void update(MockDataDto mockData) {

		Object mockDataEntity = AdaptUtil.fromSource(mockData);
		genericDao.update(mockDataEntity);
	}

	@Override
	public void delete(MockDataDto mockData) {
		Object mockDataEntity = AdaptUtil.fromSource(mockData);
		genericDao.update(mockDataEntity);
	}

	@Override
	public void deleteById(long id) {

		Adapt adapt = MockDataDto.class.getAnnotation(Adapt.class);
		genericDao.deleteById(adapt.to(), id);
	}

	@Override
	public MockDataDto getById(long id) {

		Adapt adapt = MockDataDto.class.getAnnotation(Adapt.class);
		Object entity = genericDao.getById(adapt.to(), id);

		MockDataDto mockDataDto = AdaptUtil.fromTarget(entity, MockDataDto.class);

		return mockDataDto;
	}

	@Override
	public List<MockDataDto> getElements(int offset, int limit) {

		Adapt adapt = MockDataDto.class.getAnnotation(Adapt.class);
		List<?> list = genericDao.getElements(adapt.to(), offset, limit);

		List<MockDataDto> elements = new ArrayList<MockDataDto>();
		for (Object entity : list) {

			MockDataDto mockDataDto = AdaptUtil.fromTarget(entity, MockDataDto.class);
			elements.add(mockDataDto);
		}

		return elements;
	}

	@Override
	public List<MockDataDto> getElements(DetachedCriteria criteria, int offset, int limit) {
		// TODO: revisar la forma de adaptar el criteria

		List<?> list = genericDao.getElements(criteria, offset, limit);

		List<MockDataDto> elements = new ArrayList<MockDataDto>();
		for (Object entity : list) {

			MockDataDto mockDataDto = AdaptUtil.fromTarget(entity, MockDataDto.class);
			elements.add(mockDataDto);
		}

		return elements;
	}

}
