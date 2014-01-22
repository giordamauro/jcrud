package com.jcrud.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class CompositeServletContainer extends HttpServlet {

	private static final long serialVersionUID = -1886366373810761633L;

	private static final Log logger = LogFactory.getLog(CompositeServletContainer.class);

	@Autowired
	private List<? extends HttpServlet> servlets;

	private FilterConfig filterConfig;

	@Override
	public ServletContext getServletContext() {
		if (filterConfig != null)
			return filterConfig.getServletContext();

		return super.getServletContext();
	}

	@Override
	public void init() throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
		super.init();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		init();

		if (servlets == null || servlets.size() == 0) {
			throw new IllegalStateException("There aren't any Servlets registered");
		}

		for (HttpServlet servlet : servlets) {
			servlet.init(config);
		}
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info(String.format("Servicing request '%s: %s' from ip '%s'", request.getMethod(), request.getPathInfo(), request.getRemoteAddr()));

		HttpServlet servlet = servlets.get(0);
		StatusExposingServletResponse statusResponse = new StatusExposingServletResponse(response);
		servlet.service(request, statusResponse);

		int i = 1;
		while ((statusResponse.getStatus() == 404 || statusResponse.getStatus() == 405) && i < servlets.size()) {

			servlet = servlets.get(i);
			servlet.service(request, statusResponse);
			i++;
		}

		if (statusResponse.getStatus() >= 400) {
			statusResponse.doSendError();
		}
	}
}
