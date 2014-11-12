package com.emodoki.rest.beans;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * This is bean is particularly used for login.
 * @author Sunny
 *
 */
@XmlRootElement(name = "credential")
public class Credential extends Base {
	private String email;
	private String password;
	
	public Credential() {}
	
	public Credential(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
