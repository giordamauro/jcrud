package com.jcrud.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.jcrud.model.HttpRequest;
import com.jcrud.model.HttpResponse;
import com.jcrud.model.HttpTypeAdapter;
import com.jcrud.model.impl.HttpResponseImpl;

public class JsonTypeAdapter implements HttpTypeAdapter {

	private final Gson gson;

	public JsonTypeAdapter(Gson gson) {
		this.gson = gson;
	}

	@Override
	public <T> T getFromRequest(HttpRequest request, Class<T> resourceClass) {

		InputStream content = request.getContent();
		String json = getStringBodyContent(content);

		T object = gson.fromJson(json, resourceClass);

		return object;
	}

	@Override
	public HttpResponse toHttpResponse(Object responseObject) {

		String json = gson.toJson(responseObject);
		byte[] bytes = json.getBytes();
		InputStream content = new ByteArrayInputStream(bytes);

		HttpResponseImpl response = new HttpResponseImpl(200, content);
		response.setHeader("Content-Type", "application/json");

		return response;
	}

	private String getStringBodyContent(InputStream content) {

		StringBuilder data = new StringBuilder();
		try {
			InputStreamReader reader = new InputStreamReader(content);
			char[] buf = new char[1024];
			int numRead = 0;
			while ((numRead = reader.read(buf)) != -1) {
				String readData = String.valueOf(buf, 0, numRead);
				data.append(readData);
			}
			reader.close();
			return data.toString();

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

}
