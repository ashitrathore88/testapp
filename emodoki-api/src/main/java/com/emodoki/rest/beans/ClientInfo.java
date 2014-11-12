package com.emodoki.rest.beans;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="client")
public class ClientInfo extends Base {
	
	private String 	clientName;
	@XmlElement(name="name")	
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public ClientInfo(String clientName) {
		super();
		this.clientName = clientName;
	}
	
	public ClientInfo() {
	}

}
