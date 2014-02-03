package com.jcrud.utils.adapter.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.apimock.model.impl.MockDataDto;
import com.jcrud.jpa.DaoCriteria;
import com.jcrud.jpa.GenericDao;
import com.jcrud.jpa.query.RestQueryCriteria;
import com.jcrud.query.QueryOperation;
import com.jcrud.utils.adapter.Adapt;
import com.jcrud.utils.adapter.AdaptUtil;

public class GenericAdapterDao implements GenericDao {

	private final GenericDao genericDao;

	public GenericAdapterDao(GenericDao genericDao) {
		this.genericDao = genericDao;
	}

	@Override
	public long save(Object object) {

		object = getAdaptedObject(object);
		long id = genericDao.save(object);

		return id;
	}

	@Override
	public void update(Object object) {

		object = getAdaptedObject(object);
		genericDao.update(object);

	}

	@Override
	public void delete(Object object) {
		object = getAdaptedObject(object);
		genericDao.delete(object);
	}

	@Override
	public void deleteById(Class<?> daoClass, long id) {
		daoClass = getAdaptedClass(daoClass);
		genericDao.deleteById(daoClass, id);
	}

	@Override
	public <T> T getById(Class<T> daoClass, long id) {

		Class<?> adaptClass = getAdaptedClass(daoClass);
		Object adaptElement = genericDao.getById(adaptClass, id);

		T element = AdaptUtil.fromTarget(adaptElement, daoClass);

		return element;
	}

	@Override
	public <T> List<T> getElements(Class<T> daoClass, int offset, int limit) {

		Class<?> adaptClass = getAdaptedClass(daoClass);

		List<?> list = genericDao.getElements(adaptClass, offset, limit);

		List<T> elements = new ArrayList<T>();
		for (Object entity : list) {

			T element = AdaptUtil.fromTarget(entity, daoClass);
			elements.add(element);
		}

		return elements;
	}

	@Override
	public <T> List<T> getElements(DaoCriteria<T> criteria, int offset, int limit) {

		Class<T> daoClass = criteria.getResourceClass();
		@SuppressWarnings("unchecked")
		Class<Object> adaptClass = (Class<Object>) getAdaptedClass(daoClass);
		
		DaoCriteria<?> adaptCriteria = criteria;
		
		if(!adaptClass.equals(daoClass)){
		
			Class<?> criteriaClass = criteria.getClass();
			if(criteriaClass.isAssignableFrom(RestQueryCriteria.class)){
			
				RestQueryCriteria<T> queryCriteria = (RestQueryCriteria<T>) criteria;
				QueryOperation operation = queryCriteria.getOperation();
				
				QueryOperation adaptedOperation = getAdaptedQueryOperation(operation, daoClass, adaptClass);
				
				adaptCriteria = new RestQueryCriteria<Object>(adaptedOperation, adaptClass, queryCriteria.getOrders());
			}
		}
		
		List<?> list = genericDao.getElements(adaptCriteria, offset, limit);

		List<T> elements = null;

		if(!adaptClass.equals(daoClass)){
		
			elements =  new ArrayList<T>();
			for (Object entity : list) {
	
				T element = AdaptUtil.fromTarget(entity, daoClass);
				elements.add(element);
			}
		}
		else{
			@SuppressWarnings("unchecked")
			List<T> tElements = (List<T>) list;
			elements = tElements;
		}
		
		return elements;
	}

	private Class<?> getAdaptedClass(Class<?> daoClass) {

		Adapt adapt = daoClass.getAnnotation(Adapt.class);
		if (adapt != null) {
			daoClass = adapt.to();
		}

		return daoClass;
	}

	private Object getAdaptedObject(Object object) {

		if (object != null) {

			Class<?> objectClass = object.getClass();

			if (isAdaptAnnotatedClass(objectClass)) {
				object = AdaptUtil.fromSource(object);
			}
		}
		return object;
	}

	private boolean isAdaptAnnotatedClass(Class<?> aClass) {

		Adapt adapt = aClass.getAnnotation(Adapt.class);
		boolean isAdaptAnnotated = adapt != null;

		return isAdaptAnnotated;
	}
	
	private QueryOperation getAdaptedQueryOperation(QueryOperation operation, Class<?> daoClass, Class<?> adaptClass){
		
		//ENCARGADO DE ADAPTAR LOS RENOMBRES DE CAMPOS
		//TODO: hacer recursivo
		
		return operation;
	}

	public static void main(String[] args) throws Exception {

		long start = new Date().getTime();

//		 Map<String, List<String>> headers = new HashMap<String,
//		 List<String>>();
//		 List<String> list = new ArrayList<String>();
//		 list.add("mamaaa");
//		 list.add("otroooo");
//		 headers.put("helllooo", list);
//		
//		 QueryHeaderEvaluator evaluator = new QueryHeaderEvaluator(headers,
//		 headers);
//		 SerializedEvaluator evaluator2 = new SerializedEvaluator(evaluator);
//		
//		 MockRequestEvaluator compositeEvaluator = new
//		 CompositeEvaluator(evaluator, evaluator2);
//		
//		 MockRequestDto request = new MockRequestDto(HttpMethod.DELETE,
//		 "miPath", 8, compositeEvaluator);
//		
//		 MockResponseDto response = new MockResponseDto(395, headers, null);
//		
//		 MockDataDto mockDataDto = new MockDataDto(request, response);
//		
//		 MockDataEntity entity = AdaptUtil.fromSource(mockDataDto);
//
//		 System.out.println(entity);

		@SuppressWarnings("resource")
		ApplicationContext appContext = new ClassPathXmlApplicationContext("context/applicationContext.xml");
		GenericDao adapterDao = (GenericDao) appContext.getBean("adapterDao");

//		 long id = adapterDao.save(mockDataDto);
//		 System.out.println(id);

		MockDataDto mockData = adapterDao.getById(MockDataDto.class, 1);
		System.out.println(mockData);
//
		long end = new Date().getTime();

		System.out.println("Tiempo total: " + (end - start));
	}
}
