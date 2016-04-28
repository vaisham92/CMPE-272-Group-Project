package org.cmpe272.cloud;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;
/**
 * 
 * This is test class
 * 
 * @author Vaishampayan Reddy
 *
 */
@Path("")
@Component
public class Marias {

	@GET
	@Path("/test/{param}")
	public Response getMsg(@PathParam("param") String msg) {
		String output = "Endeavour says : " + msg;
		return Response.status(200).entity(output).build();
	}

	/**
	 * This is a test method to show java doc
	 * @param abcd first param
	 * @param k
	 * @return true or false
	 */
	public boolean testMethod(String abcd, int k) {
		return true;
	}
}
