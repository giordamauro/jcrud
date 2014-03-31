package com.jcrud.impl.stub;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/person")
public class MessageRestService {

	@GET
	@Path("/{param}")
	public Response printMessage(@PathParam("param") String msg) {

		String result = "Restful example : " + msg;

		return Response.status(200).entity(result).build();
	}
	
	@POST
	@Path("/{param}")
	public Response printErrorMessage(@PathParam("param") String msg) {

		String result = "Restful error example : " + msg;

		return Response.status(401).entity(result).build();
	}

}