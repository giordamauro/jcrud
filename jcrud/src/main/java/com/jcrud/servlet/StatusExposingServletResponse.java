package com.jcrud.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class StatusExposingServletResponse extends HttpServletResponseWrapper {

	private int httpStatus;
	private String message;

	public StatusExposingServletResponse(HttpServletResponse response) {
		super(response);
	}

	@Override
	public void sendError(int sc) throws IOException {
		httpStatus = sc;
		super.sendError(sc);
	}

	@Override
	public void sendError(int sc, String msg) throws IOException {
		httpStatus = sc;
		message = msg;
		// super.sendError(sc, msg);
	}

	@Override
	public void setStatus(int sc) {
		httpStatus = sc;
		super.setStatus(sc);
	}

	public int getStatus() {
		return httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void doSendError() throws IOException {
		if (message != null) {
			super.sendError(httpStatus, message);
		} else {
			super.sendError(httpStatus);
		}
	}

}