package com.emodoki.rest.api;

import javax.jws.WebMethod;
import javax.jws.WebService;
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
 * 
 * Api Key Service has one REST API:
 * 1. getApiKey()
 * 
 * @URL
 * /api/getkey
 * 
 * @author Sunny
 * 
 */
@WebService(name = "ServiceKey", targetNamespace = IConstants.TARGET_NAMESPACE)
public interface ServiceKey {
	/**
	 * Service is used to get api key based on . 
	 */
	@WebMethod
	@GET
	@Path("/gen/{clientName}")
	@Produces({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
	@Descriptions({
		@Description(value = "Service is used to generate key. You have to pass key name as a path param.", target = DocTarget.METHOD),
		@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response generate(@PathParam("clientName") String clientName);
	
	/**
	 * Service is used to disable a key. 
	 */
	@WebMethod
	@GET
	@Path("/disable/{key}")
	@Produces({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
	@Descriptions({
		@Description(value = "Service is used to disable key. You have to pass key name as a path param.", target = DocTarget.METHOD),
		@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response disable(@PathParam("key") String key);
	
	
	/**
	 * Service is used to get a key. 
	 */
	@WebMethod
	@GET
	@Path("/get/{clientName}")
	@Produces({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON })
	@Descriptions({
		@Description(value = "Service is used to get a key. You have to pass client name as a path param.", target = DocTarget.METHOD),
		@Description(value = "Return the response entity", target = DocTarget.RETURN) })
	Response get(@PathParam("clientName") String clientName);
}
