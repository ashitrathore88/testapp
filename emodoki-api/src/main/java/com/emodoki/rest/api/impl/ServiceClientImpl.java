package com.emodoki.rest.api.impl;

import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emodoki.dao.DaoClient;
import com.emodoki.model.Client;
import com.emodoki.rest.api.ServiceClient;

/**
 * 
 * @author Sunny
 *
 */
@Service("serviceClientImpl")
@Transactional
public class ServiceClientImpl extends ServiceBase implements ServiceClient {
	
	Log log = LogFactory.getLog(ServiceClientImpl.class);
	private String message = "success";
	
	@Autowired
	DaoClient daoClient;
	
	@Override
	@Transactional
	public Response add(String name) {
		log.info("executing ---> add()");
		if(name!=null && !"".equals(name)) {
			Client client = new Client();
			client.setClientName(name);
			try {
				daoClient.save(client);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Success();
			log.info("add() --> OK");
		}else {
			Failed();
			log.info("add() --> Failed");
		}
		emodokiResponse.setMessage(message);
		builder.entity(emodokiResponse);
		log.info("executed ---> add()");
		builder.type(getMediaType());
		return builder.build();
	}
	
	@Override
	@Transactional
	public Response remove(String name) {
		log.info("executing ---> remove()");
		if(name!=null && !"".equals(name)) {
			try{
			daoClient.delete(name);
			}catch (Exception e) {
				e.printStackTrace();
				Failed();
			}
			Success();
			log.info("remove() --> OK");
		}else {
			Failed(); 
			log.info("remove() --> Failed");
		}
		emodokiResponse.setMessage(message);
		builder.entity(emodokiResponse);
		log.info("executed ---> remove()");
		builder.type(getMediaType());
		return builder.build();
	}
	
	
}
