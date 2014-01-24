package com.apimock.model;

import java.util.List;

import com.jcrud.model.HttpMethod;

public interface MockRepository {

	List<MockData> getMocksData(HttpMethod method, String path);
}
