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

import com.jcrud.model.HttpMethod;

@Entity
@Table(name = "mockrequest")
public class MockRequestEntity {

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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public MockRequestEvaluatorEntity getEvaluator() {
		return evaluator;
	}

	public void setEvaluator(MockRequestEvaluatorEntity evaluator) {
		this.evaluator = evaluator;
	}
}
