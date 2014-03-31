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


@Entity
@Table(name = "mockdata")
public class MockDataEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique = true, nullable = false)
	private String uuid;

	@ManyToOne
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "request_id", nullable = false)
	private MockRequestEntity request;

	@ManyToOne
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "response_id", nullable = false)
	private MockResponseEntity response;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public MockRequestEntity getRequest() {
		return request;
	}

	public void setRequest(MockRequestEntity request) {
		this.request = request;
	}

	public MockResponseEntity getResponse() {
		return response;
	}

	public void setResponse(MockResponseEntity response) {
		this.response = response;
	}
}
