package com.jcrud.jpa.query;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.jcrud.jpa.DaoCriteria;
import com.jcrud.jpa.GenericDao;
import com.jcrud.jpa.hibernate.HibernateCriteria;
import com.jcrud.query.QueryOperation;
import com.jcrud.utils.query.QueryUtil;

public class RestQueryCriteria<T> implements DaoCriteria<T>{

	private final QueryOperation operation;
	
	private final Class<T> resourceClass;
	
	private final List<Order> orders;

	public RestQueryCriteria(String query, Class<T> resourceClass, List<Order> orders) {
		
		QueryOperation queryOp = null;
		if(query!= null){
			queryOp = QueryUtil.getQueryOperation(query);
		}
		
		this.operation = queryOp;
		this.resourceClass = resourceClass;
		this.orders = orders;
	}
	
	public RestQueryCriteria(QueryOperation operation, Class<T> resourceClass, List<Order> orders) {
		this.operation = operation;
		this.resourceClass = resourceClass;
		this.orders = orders;
	}

	@Override
	public List<T> getElements(GenericDao genericDao, int offset, int limit) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(resourceClass);

		if(operation != null){
			Criterion criterion = QueryUtil.getQueryCritierion(operation, resourceClass);
			criteria.add(criterion);
		}
		
		for (Order order : orders) {
			criteria.addOrder(order);
		}
		
		HibernateCriteria<T> hibernateCriteria = new HibernateCriteria<T>(criteria, resourceClass);
		List<T> elements = hibernateCriteria.getElements(genericDao, offset, limit);
		
		return elements;
	}

	@Override
	public Class<T> getResourceClass() {
		return resourceClass;
	}
	
	public QueryOperation getOperation(){
		return operation;
	}

	public List<Order> getOrders() {
		return orders;
	}
}
