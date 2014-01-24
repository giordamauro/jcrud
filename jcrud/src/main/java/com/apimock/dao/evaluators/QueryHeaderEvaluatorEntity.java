package com.apimock.dao.evaluators;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.apimock.dao.MockRequestEvaluatorEntity;
import com.apimock.model.MockRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcrud.model.HttpRequest;

@Entity
@Table(name = "queryheaderevaluator")
@PrimaryKeyJoinColumn(name = "id")
public class QueryHeaderEvaluatorEntity extends MockRequestEvaluatorEntity {

	private static final long serialVersionUID = 928191933593617079L;

	private static final Type headersMapType = new TypeToken<Map<String, List<String>>>() {
	}.getType();

	private static final Gson gson = new Gson();

	@Column
	private String queryParams;

	@Column
	private String headers;

	public QueryHeaderEvaluatorEntity() {

	}

	public QueryHeaderEvaluatorEntity(Map<String, List<String>> queryParams, Map<String, List<String>> headers) {
		setQueryParams(queryParams);
		setHeaders(headers);
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hashCodeBuilder = new HashCodeBuilder(17, 31);
		hashCodeBuilder.append(headers);
		hashCodeBuilder.append(queryParams);

		int hashCode = hashCodeBuilder.toHashCode();

		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj == this)
			return true;
		if (!(obj instanceof QueryHeaderEvaluatorEntity))
			return false;

		QueryHeaderEvaluatorEntity serviceMatcher = (QueryHeaderEvaluatorEntity) obj;
		EqualsBuilder equalsBuilder = new EqualsBuilder();
		equalsBuilder.append(queryParams, serviceMatcher.queryParams);
		equalsBuilder.append(headers, serviceMatcher.headers);

		return equalsBuilder.isEquals();
	}

	@Override
	public boolean evaluate(MockRequest mockRequest, HttpRequest request) {

		boolean eval = false;

		if (queryParams != null) {

			Map<String, List<String>> queries = gson.fromJson(queryParams, headersMapType);
			eval = compareParameters(queries, request.getQueryParams());
		}

		if (eval && headers != null) {
			Map<String, List<String>> headerParams = gson.fromJson(headers, headersMapType);
			eval = compareParameters(headerParams, request.getHeaders());
		}

		return eval;
	}

	public Map<String, List<String>> getHeaders() {
		return gson.fromJson(headers, headersMapType);
	}

	public void setHeaders(Map<String, List<String>> headers) {
		this.headers = gson.toJson(headers);
	}

	public Map<String, List<String>> getQueryParams() {
		return gson.fromJson(queryParams, headersMapType);
	}

	public void setQueryParams(Map<String, List<String>> queryParams) {
		this.queryParams = gson.toJson(queryParams);
	}

	private boolean compareParameters(Map<String, List<String>> parameters, Map<String, List<String>> serviceParameters) {

		for (Entry<String, List<String>> param : parameters.entrySet()) {
			String paramName = param.getKey();

			List<String> serviceValues = serviceParameters.get(paramName);
			if (serviceValues == null) {
				return false;
			}

			List<String> values = param.getValue();
			for (String value : values) {
				if (!serviceValues.contains(value)) {
					return false;
				}
			}
		}
		return true;
	}

}
