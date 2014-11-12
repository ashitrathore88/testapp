package com.emodoki.rest.beans;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="key")
public class KeyInfo extends Base {
	
	private String key;
	private Long client;
		
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public Long getClient() {
		return client;
	}

	public void setClient(Long client) {
		this.client = client;
	}

	public KeyInfo(String key, Long client) {
		super();
		this.key = key;
		this.client = client;
	}

	public KeyInfo() {
	}

}
