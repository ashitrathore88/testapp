package com.emodoki.rest.api.impl;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emodoki.dao.DaoMood;
import com.emodoki.model.Mood;
import com.emodoki.rest.api.ServiceMood;
import com.emodoki.rest.utils.Toolbox;

/**
 * 
 * @author Sunny
 * 
 */
@Service("serviceMoodImpl")
@Transactional
public class ServiceMoodImpl extends ServiceBase implements ServiceMood {

	Log log = LogFactory.getLog(ServiceMoodImpl.class);
	private String message = "success";

	@Autowired
	DaoMood daoMood;

	@Override
	@Transactional
	public Response add(Mood mood) {
		log.info("executing ---> add()");
		if (mood != null && isKeyValid(mood.getKey())) {
			if (mood.getDescription() != null
					&& !"".equals(mood.getDescription())) {

				try {
					daoMood.save(mood);
					Success();
				} catch (Exception e) {
					e.printStackTrace();
					Failed();
				}
				log.info("add() --> OK");
			} else {
				NotAcceptable();
				log.info("add() --> Failed");
			}
		} else {
			log.info("add() --> Invalid Key. ");
			builder = Response.status(Status.FORBIDDEN);
			message = getMessage("msg.failed", null,
					Toolbox.getLocaleFromString(mood.getLocale()));
			emodokiResponse.setStatus(Status.FORBIDDEN.getStatusCode());
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
		if (name != null && !"".equals(name)) {
			daoMood.deleteByName(name);
			Success();
			log.info("remove() --> OK");
		} else {
			NotAcceptable();
			log.info("remove() --> Failed");
		}
		setEntity();
		log.info("executed ---> remove()");
		return builder.build();
	}

	@Override
	@Transactional
	public Response update(Mood mood) {
		log.info("executing ---> update()");
		if (mood != null && mood.getDescription() != null
				&& !"".equals(mood.getDescription()) && mood.getId() > 0) {
			try {
				daoMood.save(mood);
				Success();
			} catch (Exception e) {
				e.printStackTrace();
				Failed();
			}
			log.info("update() --> OK");
		} else {
			NotAcceptable();
			log.info("update() --> Failed");
		}
		setEntity();
		log.info("executed ---> update()");
		return builder.build();
	}

	@Override
	public Response get(@PathParam("name") String name) {
		log.info("executing ---> remove()");
		if (name != null && !"".equals(name)) {
			Success();
			Mood mood = daoMood.findByName(name);
			setEntity(mood);
			log.info("remove() --> OK");
		} else {
			NotAcceptable();
			log.info("remove() --> Failed");
			setEntity();
		}
		log.info("executed ---> remove()");
		return builder.build();
	}

}
