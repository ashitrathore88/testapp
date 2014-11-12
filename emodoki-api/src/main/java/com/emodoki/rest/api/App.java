package com.emodoki.rest.api;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
@Component
public class App {
	
	@Autowired
	MessageSource messageSource;
	
	
	public App() {
		super();
	}

	/**
	 * Getting message from resource bundle
	 * 
	 * @param key
	 * @return
	 */
	public String getMessage(String key) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(key, null, locale);
	}

	/**
	 * Getting message from resource bundle and passing dynamic arguments
	 * 
	 * @param key
	 * @param args
	 * @return
	 */
	public String getMessage(String key, Object[] args) {
		return messageSource.getMessage(key, args, null);
	}

	/**
	 * Getting message from resource bundle, passing dynamic arguments and
	 * locale
	 * 
	 * @param key
	 * @param args
	 * @param locale
	 * @return
	 */
	public String getMessage(String key, Object[] args, Locale locale) {
		return messageSource.getMessage(key, args, locale);
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	
}
