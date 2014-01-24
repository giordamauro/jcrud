package com.jcrud.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.jcrud.jpa.GenericDao;
import com.jcrud.model.HttpRequest;
import com.jcrud.model.RestHandler;
import com.jcrud.model.exceptions.CRUDResourceNotExistent;
import com.jcrud.utils.query.QueryUtil;

public class DaoRestHandler implements RestHandler {

	private static final int DEFAULT_ELEMENTS_COUNT = 20;
	private static final int DEFAULT_PAGE_NUMBER = 0;

	private final GenericDao genericDao;

	public DaoRestHandler(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	@Override
	public <T> T handlePOST(T newObject) {

		@SuppressWarnings("unchecked")
		Class<T> resourceClass = (Class<T>) newObject.getClass();
		long id = genericDao.save(newObject);
		T object = genericDao.getById(resourceClass, id);

		return object;
	}

	@Override
	public <T> T handlePUT(long id, T objectUpdates) throws CRUDResourceNotExistent {
		@SuppressWarnings("unchecked")
		Class<T> resourceClass = (Class<T>) objectUpdates.getClass();

		genericDao.update(objectUpdates);
		T object = genericDao.getById(resourceClass, id);
		return object;
	}

	@Override
	public void handleDELETE(Class<?> resourceClass, long id) throws CRUDResourceNotExistent {
		Object object = genericDao.getById(resourceClass, id);
		if (object == null) {
			throw new CRUDResourceNotExistent(resourceClass, id);
		}
		genericDao.deleteById(resourceClass, id);
	}

	@Override
	public <T> T handleGET(Class<T> resourceClass, long id) throws CRUDResourceNotExistent {
		T object = genericDao.getById(resourceClass, id);
		return object;
	}

	@Override
	public <T> List<T> handleGET(HttpRequest request, Class<T> resourceClass) {

		int elementsCount = getIntegerQueryParam(request, "elementsCount");
		if (elementsCount == -1) {
			elementsCount = DEFAULT_ELEMENTS_COUNT;
		}

		int pageNumber = getIntegerQueryParam(request, "pageNumber");
		if (pageNumber == -1) {
			pageNumber = DEFAULT_PAGE_NUMBER;
		}

		DetachedCriteria criteria = DetachedCriteria.forClass(resourceClass);

		String query = getQueryFilter(request);
		if (query != null) {
			Criterion criterion = QueryUtil.getCriterionForQuery(query, resourceClass);
			criteria.add(criterion);
		}

		List<Order> orders = getOrders(request);
		for (Order order : orders) {
			criteria.addOrder(order);
		}

		int offset = elementsCount * pageNumber;
		List<T> elements = genericDao.getElements(criteria, offset, elementsCount);

		return elements;
	}

	private String getQueryFilter(HttpRequest request) {

		String filterParamName = "filter";

		String query = request.getQueryParam(filterParamName);

		if (query != null) {
			try {
				query = URLDecoder.decode(query, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new IllegalStateException(e);
			}
		} else {
			query = request.getHeader(filterParamName);
		}

		return query;
	}

	private List<Order> getOrders(HttpRequest request) {

		List<Order> orders = new ArrayList<Order>();

		List<String> orderValues = request.getQueryParamValues("order");
		List<String> orderTypeValues = request.getQueryParamValues("orderType");

		if (orderValues != null) {

			for (int i = 0; i < orderValues.size(); i++) {

				String orderValue = orderValues.get(i);
				String orderType = "asc";

				if (orderTypeValues != null && i < orderTypeValues.size()) {
					orderType = orderTypeValues.get(i);
				}

				if (orderType.equalsIgnoreCase("asc")) {
					orders.add(Order.asc(orderValue));

				} else if (orderType.equalsIgnoreCase("desc")) {
					orders.add(Order.desc(orderValue));

				} else {
					throw new IllegalStateException(String.format("Cannot order with type '%s'", orderType));
				}
			}
		}

		return orders;
	}

	private int getIntegerQueryParam(HttpRequest request, String queryName) {

		int intValue = -1;

		String queryParam = request.getQueryParam(queryName);
		if (queryParam != null) {
			return Integer.valueOf(queryParam);
		}

		return intValue;
	}
}
