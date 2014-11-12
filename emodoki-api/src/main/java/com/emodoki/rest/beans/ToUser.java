package com.emodoki.rest.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="toUser")
public class ToUser {
	
public String username;

public String getUsername() {
	return username;
}

/*public void setUsername(String username) {
	this.username = username;
}*/

}
