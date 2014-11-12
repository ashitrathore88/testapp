package com.emodoki.rest.api;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import com.emodoki.model.Mood;


/**
 * @author Sunny
 */
@WebService(name = "ServiceMood", targetNamespace = IConstants.TARGET_NAMESPACE)
public interface ServiceMood {
	/**
	 * Service is used to add an client. 
	 */
	@WebMethod
	@POST
	@Path("/add")
	@Produces({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
	@Descriptions({
		@Description(value = "Service is used to create a mood.", target = DocTarget.METHOD),
		@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response add(Mood mood);
	
	/**
	 * Service is used to add an client. 
	 */
	@WebMethod
	@POST
	@Path("/update")
	@Produces({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
	@Descriptions({
		@Description(value = "Service is used to update a mood.", target = DocTarget.METHOD),
		@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response update(Mood mood);
	
	/**
	 * Service is used to remove an client. 
	 */
	@WebMethod
	@GET
	@Path("/remove/{name}")
	@Produces({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
	@Descriptions({
		@Description(value = "Service is used to remove a mood.", target = DocTarget.METHOD),
		@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response remove(@PathParam("name") String name);
	
	/**
	 * Service is used to get an client. 
	 */
	@WebMethod
	@GET
	@Path("/get/{name}")
	@Produces({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
	@Descriptions({
		@Description(value = "Service is used to get a mood.", target = DocTarget.METHOD),
		@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response get(@PathParam("name") String name);
}
