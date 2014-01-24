package com.apimock.model;

import com.apimock.model.exceptions.MockServiceMatcherConflict;
import com.apimock.model.exceptions.MockServiceNotFoundException;
import com.jcrud.model.HttpRequest;

public interface MockRepositoryConsumer {

	MockResponse getMockResponse(HttpRequest request) throws MockServiceNotFoundException, MockServiceMatcherConflict;
}
