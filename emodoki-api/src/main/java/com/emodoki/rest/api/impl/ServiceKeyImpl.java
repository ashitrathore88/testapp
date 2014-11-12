package com.emodoki.rest.api.impl;

import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emodoki.dao.DaoClient;
import com.emodoki.dao.DaoKey;
import com.emodoki.model.Client;
import com.emodoki.model.Key;
import com.emodoki.rest.api.ServiceKey;
import com.emodoki.rest.utils.Toolbox;

/**
 * 
 * @author Sunny
 *
 */
@Service("serviceKeyImpl")
@Transactional
public class ServiceKeyImpl extends ServiceBase implements ServiceKey {
	
	Log log = LogFactory.getLog(ServiceKeyImpl.class);
	private String message = "success";
	
	@Autowired
	DaoClient daoClient;
	
	@Autowired
	DaoKey daoKey;
	
	@Override
	@Transactional
	public Response generate(String clientName) {
		log.info("executing ---> generate()");
		Client client = daoClient.get(clientName);
		if(client!=null) {
			message = Toolbox.getUUID();
			Key key = new Key();
			key.setKeyValue(message);
			key.setClient(client);
			try {
				daoKey.save(key);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.info("generate() --> OK");
			Success();
		}else {
			NoContent();
		}
		emodokiResponse.setMessage(message);
		builder.entity(emodokiResponse);
		builder.type(getMediaType());
		log.info("executed ---> generate()");
		return builder.build();
	}
	
	@Override
	@Transactional
	public Response disable(String key) {
		log.info("executing ---> disable()");
		boolean isDisabled = daoKey.disable(key);
		if(isDisabled==true) {
			Success();
			log.info("disable() --> OK");
		}else {
			NoContent();
		}
		
		log.info("executed ---> disable()");
		return builder.build();
	}
	
	@Override
	@Transactional
	public Response get(String clientName) {
		log.info("executing ---> get()");
		if(clientName!=null && !"".equals(clientName)) {
			Key key = daoKey.get(clientName);
			if(key!=null) {
				message = key.getKeyValue();
				Success();
			}else {
				NoContent();
			}
			
			log.info("get() --> OK");
		}else {
			NoContent();
		}
		emodokiResponse.setMessage(message);
		builder.entity(emodokiResponse);
		builder.type(getMediaType());
		log.info("executed ---> get()");
		return builder.build();
	}
	
	
}
