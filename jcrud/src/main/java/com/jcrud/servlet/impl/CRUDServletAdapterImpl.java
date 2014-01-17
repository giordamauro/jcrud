package com.jcrud.servlet.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jcrud.model.HttpRequest;
import com.jcrud.model.HttpResponse;
import com.jcrud.model.impl.HttpRequestImpl;
import com.jcrud.servlet.CRUDServletAdapter;

public class CRUDServletAdapterImpl implements CRUDServletAdapter {

	@Override
	public HttpRequest adaptRequest(HttpServletRequest request) {

		String method = request.getMethod();
		String path = request.getPathInfo();

		InputStream content;
		try {
			content = request.getInputStream();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		HttpRequestImpl requestImpl = new HttpRequestImpl(method, path, content);

		Map<String, List<String>> headers = getHeaders(request);
		requestImpl.setHeaders(headers);

		String queryString = request.getQueryString();
		if (queryString != null) {
			Map<String, List<String>> queryParams = getQueryParameters(queryString);
			requestImpl.addQueryParams(queryParams);
		}
		return requestImpl;
	}

	@Override
	public void adaptResponse(HttpResponse httpResponse, HttpServletResponse response) {

		int status = httpResponse.getStatus();
		Map<String, String> headers = httpResponse.getHeaders();
		InputStream content = httpResponse.getContent();

		response.setStatus(status);
		for (Entry<String, String> header : headers.entrySet()) {
			response.addHeader(header.getKey(), header.getValue());
		}

		setResponsePayload(content, response);
	}

	private Map<String, List<String>> getHeaders(HttpServletRequest request) {

		Map<String, List<String>> headers = new HashMap<String, List<String>>();

		@SuppressWarnings("unchecked")
		Enumeration<String> headerNames = (Enumeration<String>) request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			String value = request.getHeader(headerName);

			String[] values = value.split(";");
			List<String> listValues = Arrays.asList(values);

			headers.put(headerName, listValues);
		}
		return headers;
	}

	private Map<String, List<String>> getQueryParameters(String queryString) {
		Map<String, List<String>> queryParams = new HashMap<String, List<String>>();

		String[] params = queryString.split("&");
		for (String param : params) {
			String[] split = param.split("=");
			if (split.length > 1) {
				String key = split[0];
				String value = split[1];

				addQueryParam(queryParams, key, value);
			}
		}
		return queryParams;
	}

	private void addQueryParam(Map<String, List<String>> queryParams, String param, String value) {
		List<String> values = queryParams.get(param);
		if (values == null) {
			values = new ArrayList<String>();
			queryParams.put(param, values);
		}
		values.add(value);
	}

	private void setResponsePayload(InputStream in, HttpServletResponse response) {

		try {
			OutputStream out = response.getOutputStream();

			byte[] buffer = new byte[4096];
			int read = 0;

			while ((read = in.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
			in.reset();

		} catch (IOException e) {

			throw new IllegalStateException(e);
		}
	}

}
