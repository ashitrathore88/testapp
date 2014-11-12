package com.emodoki.rest.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * API response
 * @author Sunny
 *
 */
@XmlRootElement(name="response")
public class EmodokiResponse {
	private int status;
	private String message;
	//private String email;

	public EmodokiResponse() {}
	
	public EmodokiResponse(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

		
	
	
}
