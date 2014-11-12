package com.emodoki.rest.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="login")
public class Login extends Base {
	
private String email;
private String facebookId;
private String apiKey;

public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getFacebookId() {
	return facebookId;
}
public void setFacebookId(String facebookId) {
	this.facebookId = facebookId;
}
public String getApiKey() {
	return apiKey;
}
public void setApiKey(String apiKey) {
	this.apiKey = apiKey;
}


}
