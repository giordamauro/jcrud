package com.apimock.servlet.impl;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.apimock.model.MockRepositoryConsumer;
import com.apimock.model.MockResponse;
import com.apimock.model.exceptions.MockServiceMatcherConflict;
import com.apimock.model.exceptions.MockServiceNotFoundException;
import com.apimock.servlet.MockDispatcher;
import com.jcrud.impl.ExceptionResponse;
import com.jcrud.model.HttpRequest;
import com.jcrud.model.HttpResponse;
import com.jcrud.model.impl.HttpResponseImpl;

public class MockDispatcherImpl implements MockDispatcher {

	private final MockRepositoryConsumer repositoryConsumer;

	public MockDispatcherImpl(MockRepositoryConsumer repositoryConsumer) {
		this.repositoryConsumer = repositoryConsumer;
	}

	@Override
	public HttpResponse handleRequest(HttpRequest request) {

		try {
			MockResponse mockResponse = repositoryConsumer.getMockResponse(request);

			int status = mockResponse.getStatusCode();
			byte[] content = mockResponse.getContent();

			HttpResponseImpl response = new HttpResponseImpl(status, new ByteArrayInputStream(content));

			Map<String, List<String>> headers = mockResponse.getHeaders();
			setResponseHeaders(headers, response);

			return response;

		} catch (MockServiceNotFoundException e) {

			return ExceptionResponse.new400(String.format("Service not found"));

		} catch (MockServiceMatcherConflict e) {

			return ExceptionResponse.new400("Service matcher conflict - update priorities");
		}
	}

	private void setResponseHeaders(Map<String, List<String>> headers, HttpResponseImpl response) {

		for (Entry<String, List<String>> entry : headers.entrySet()) {
			String headerName = entry.getKey();
			List<String> values = entry.getValue();

			String completeValue = "";
			for (String value : values) {
				completeValue += value + ";";
			}
			if (!values.isEmpty()) {
				completeValue = completeValue.substring(0, completeValue.length() - 1);
			}
			response.setHeader(headerName, completeValue);
		}
	}
}
