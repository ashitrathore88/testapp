package com.emodoki.rest.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class containing the basic info of the user.
 * @author Nikhil
 *
 */
@XmlRootElement(name="basicInfo")
public class BasicInfo extends Base {
	private String userName;
	
	/**
	 * Constructs a basic info bean.
	 */
	BasicInfo() {
	}

	/** Getter Setters **/
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
