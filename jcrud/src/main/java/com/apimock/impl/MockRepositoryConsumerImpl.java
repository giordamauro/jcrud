package com.apimock.impl;

import java.util.ArrayList;
import java.util.List;

import com.apimock.model.MockData;
import com.apimock.model.MockRepository;
import com.apimock.model.MockRepositoryConsumer;
import com.apimock.model.MockRequest;
import com.apimock.model.MockResponse;
import com.apimock.model.exceptions.MockServiceMatcherConflict;
import com.apimock.model.exceptions.MockServiceNotFoundException;
import com.jcrud.model.HttpRequest;

public class MockRepositoryConsumerImpl implements MockRepositoryConsumer {

	private final MockRepository mockRepository;

	public MockRepositoryConsumerImpl(MockRepository mockRepository) {
		this.mockRepository = mockRepository;
	}

	@Override
	public MockResponse getMockResponse(HttpRequest request) throws MockServiceNotFoundException, MockServiceMatcherConflict {

		List<MockData> mocksData = mockRepository.getMocksData(request.getMethod(), request.getPath());

		MockData responseMockData = null;

		List<MockData> evaluatedMocksData = new ArrayList<MockData>();
		if (mocksData != null && !mocksData.isEmpty()) {

			for (MockData mockData : mocksData) {
				MockRequest mockRequest = mockData.getRequest();
				if (mockRequest.evaluate(request)) {
					evaluatedMocksData.add(mockData);
				}
			}
		}

		if (!evaluatedMocksData.isEmpty()) {
			int priority = -1;
			for (MockData mockData : evaluatedMocksData) {
				MockRequest mockRequest = mockData.getRequest();
				int requestPriority = mockRequest.getPriority();
				if (requestPriority > priority) {
					priority = requestPriority;
					responseMockData = mockData;
				} else if (requestPriority == priority) {
					throw new MockServiceMatcherConflict(responseMockData.getUuid(), mockData.getUuid());
				}
			}
		}

		if (responseMockData != null) {
			return responseMockData.getResponse();
		}
		throw new MockServiceNotFoundException(request);
	}
}
