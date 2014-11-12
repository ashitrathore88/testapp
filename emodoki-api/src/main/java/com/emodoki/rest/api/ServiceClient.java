package com.emodoki.rest.api;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;


/**
 * @author Sunny
 */
@WebService(name = "ServiceClient", targetNamespace = IConstants.TARGET_NAMESPACE)
public interface ServiceClient {
	/**
	 * Service is used to add an client. 
	 */
	@WebMethod
	@GET
	@Path("/add/{name}")
	@Produces({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
	@Descriptions({
		@Description(value = "Service is used to create a client.", target = DocTarget.METHOD),
		@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response add(@PathParam("name") String  name);
	
	/**
	 * Service is used to remove an client. 
	 */
	@WebMethod
	@GET
	@Path("/remove/{name}")
	@Produces({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
	@Descriptions({
		@Description(value = "Service is used to remove a client.", target = DocTarget.METHOD),
		@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response remove(@PathParam("name") String name);
}
