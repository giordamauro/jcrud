package com.apimock.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.apimock.dao.evaluators.CompositeEvaluatorEntity;
import com.apimock.dao.evaluators.QueryHeaderEvaluatorEntity;
import com.apimock.dao.evaluators.SerializedEvaluatorEntity;
import com.apimock.model.MockData;
import com.apimock.model.MockRequest;
import com.apimock.model.MockResponse;
import com.jcrud.jpa.GenericDao;
import com.jcrud.model.HttpMethod;

@Entity
public class MockDataEntity implements MockData {

	@Id
	private long id;

	@ManyToOne
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "request_id")
	private MockRequestEntity request;

	@ManyToOne
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "response_id")
	private MockResponseEntity response;

	public MockDataEntity() {

	}

	public MockDataEntity(MockRequestEntity request, MockResponseEntity response) {
		this.request = request;
		this.response = response;
	}

	@Override
	public MockRequest getRequest() {
		return request;
	}

	@Override
	public MockResponse getResponse() {
		return response;
	}

	public static void main(String[] args) throws IOException {

		@SuppressWarnings("resource")
		ApplicationContext appContext = new ClassPathXmlApplicationContext("context/applicationContext.xml");
		GenericDao dao = appContext.getBean(GenericDao.class);

		Map<String, List<String>> headersMap = new HashMap<String, List<String>>();
		List<String> list = new ArrayList<String>();
		list.add("mamaaa");
		list.add("otroooo");
		headersMap.put("helllooo", list);

		MockRequestEntity mockRequest = new MockRequestEntity();
		QueryHeaderEvaluatorEntity evaluator1 = new QueryHeaderEvaluatorEntity();
		evaluator1.setHeaders(headersMap);
		evaluator1.setQueryParams(headersMap);

		QueryHeaderEvaluatorEntity evaluator = new QueryHeaderEvaluatorEntity();
		evaluator.setHeaders(headersMap);
		evaluator.setQueryParams(headersMap);

		SerializedEvaluatorEntity evaluator2 = new SerializedEvaluatorEntity(evaluator);

		MockRequestEvaluatorEntity compositeEvaluator = new CompositeEvaluatorEntity(evaluator1, evaluator2);
		mockRequest.setEvaluator(compositeEvaluator);
		mockRequest.setMethod(HttpMethod.GET);
		mockRequest.setPath("/mypath");
		mockRequest.setPriority(2);

		MockResponseEntity responseImpl = new MockResponseEntity(302, headersMap, "hola que haces".getBytes());

		MockData mockData = new MockDataEntity(mockRequest, responseImpl);

		dao.save(mockData);

		// MockData mockData = dao.getById(MockDataEntity.class, 3);
		// System.out.println(mockData.getRequest().getMethod());
	}
}
