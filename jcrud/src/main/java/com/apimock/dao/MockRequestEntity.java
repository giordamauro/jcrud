package com.apimock.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.apimock.model.MockRequest;
import com.jcrud.model.HttpMethod;
import com.jcrud.model.HttpRequest;

@Entity
@Table(name = "mockrequest")
public class MockRequestEntity implements MockRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long id;

	@Column
	private HttpMethod method;

	@Column
	private String path;

	@Column
	private int priority;

	@ManyToOne
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "evaluator_id")
	private MockRequestEvaluatorEntity evaluator;

	@Override
	public HttpMethod getMethod() {
		return method;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public boolean evaluate(HttpRequest request) {
		return evaluator.evaluate(this, request);
	}

	@Override
	public int getPriority() {
		return priority;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setEvaluator(MockRequestEvaluatorEntity evaluator) {
		this.evaluator = evaluator;
	}

	public MockRequestEvaluatorEntity getEvaluator() {
		return this.evaluator;
	}
}
