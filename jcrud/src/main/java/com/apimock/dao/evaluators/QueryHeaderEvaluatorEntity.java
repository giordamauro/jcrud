package com.apimock.dao.evaluators;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.apimock.dao.MockRequestEvaluatorEntity;

@Entity
@Table(name = "queryheaderevaluator")
@PrimaryKeyJoinColumn(name = "id")
public class QueryHeaderEvaluatorEntity extends MockRequestEvaluatorEntity {

	private static final long serialVersionUID = 928191933593617079L;

	@Column
	private String queryParams;

	@Column
	private String headers;

	public String getQueryParams() {
		return queryParams;
	}

	public void setQueryParams(String queryParams) {
		this.queryParams = queryParams;
	}

	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}
}
