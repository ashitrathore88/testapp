package com.emodoki.rest.beans;


import javax.xml.bind.annotation.XmlElement;






/**
 * Parent class of all entities
 * @author Sunny
 *
 */
public abstract class Base {
	
	private String key;
	private String locale;	
	
	Base() {
		locale = "en_US";
	}
	
	@XmlElement(name="key",required=true)
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	@XmlElement(nillable=true)
	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	
	
}
