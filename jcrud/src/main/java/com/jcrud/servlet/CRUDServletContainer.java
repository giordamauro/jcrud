package com.jcrud.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.jcrud.model.CRUDDispatcher;
import com.jcrud.model.HttpRequest;
import com.jcrud.model.HttpResponse;

public class CRUDServletContainer extends HttpServlet implements Filter {

	private static final long serialVersionUID = -1886366373810761633L;

	private static final Log logger = LogFactory.getLog(CRUDServletContainer.class);

	@Autowired
	private CRUDDispatcher restDispatcher;

	@Autowired
	private CRUDServletAdapter crudServletAdapter;

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
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		init();
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		logger.info(String.format("Servicing request '%s: %s' from ip '%s'", request.getMethod(), request.getPathInfo(), request.getRemoteAddr()));

		HttpRequest httpRequest = crudServletAdapter.adaptRequest(request);

		HttpResponse httpResponse = restDispatcher.handleRequest(httpRequest);

		crudServletAdapter.adaptResponse(httpResponse, response);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		if (!request.getClass().isAssignableFrom(HttpServletRequest.class) || !response.getClass().isAssignableFrom(HttpServletResponse.class)) {
			throw new ServletException("non-HTTP request or response");
		}

		doHttpFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
	}

	private void doHttpFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
		try {
			service(request, response);
			chain.doFilter(request, response);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

}
