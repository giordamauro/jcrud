package com.jcrud.model;

/**
 * The Interface CRUDDispatcher for handling HttpRequests to registered
 * Resources.
 * 
 * @author Mauro Giorda
 */
public interface CRUDDispatcher {

	/**
	 * Handle request.
	 * 
	 * @param request
	 *            the request
	 * @return the http response
	 */
	HttpResponse handleRequest(HttpRequest request);

	/**
	 * Register resource.
	 * 
	 * @param path
	 *            the path
	 * @param resourceClass
	 *            the resource class
	 */
	void registerResource(String path, Class<?> resourceClass);
}
