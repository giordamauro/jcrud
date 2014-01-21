package com.jcrud.parsers;

import java.util.List;
import java.util.Map;

import com.jcrud.model.HttpRequest;
import com.jcrud.model.HttpResponse;
import com.jcrud.model.HttpTypeAdapter;

public class DispatcherTypeAdapter implements HttpTypeAdapter {

	private static final String DEFAULT_FORMAT = "application/json";

	private final Map<String, HttpTypeAdapter> registeredAdapters;

	public DispatcherTypeAdapter(Map<String, HttpTypeAdapter> registeredAdapters) {
		this.registeredAdapters = registeredAdapters;
	}

	@Override
	public <T> T getFromRequest(HttpRequest request, Class<T> resourceClass) {

		String contentType = null;
		List<String> headerValues = request.getHeader("content-type");
		if (headerValues != null && headerValues.size() == 1) {
			contentType = headerValues.get(0);
		}

		if (contentType != null) {

			HttpTypeAdapter adapter = registeredAdapters.get(contentType);
			if (adapter != null) {
				T object = adapter.getFromRequest(request, resourceClass);
				return object;

			} else {
				throw new IllegalStateException(String.format("Cannot parse content-type '%s'", contentType));
			}
		}
		throw new IllegalStateException("Content must have a Content-type");
	}

	@Override
	public HttpResponse toHttpResponse(HttpRequest request, Object responseObject) {

		String format = request.getQueryParam("format");

		HttpTypeAdapter adapter = null;

		if (format != null) {
			adapter = registeredAdapters.get("application/" + format);
		} else {
			adapter = registeredAdapters.get(DEFAULT_FORMAT);
		}

		if (adapter != null) {
			HttpResponse response = adapter.toHttpResponse(request, responseObject);
			return response;

		} else if (format != null) {
			throw new IllegalStateException(String.format("Cannot parse content-type '%s'", format));
		}

		throw new IllegalStateException(String.format("Default typeAdapter '%s' is not registered", DEFAULT_FORMAT));
	}
}
