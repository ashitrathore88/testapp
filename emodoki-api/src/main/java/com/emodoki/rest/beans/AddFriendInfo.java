package com.emodoki.rest.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class containing the basic info of the user.
 * @author Nikhil
 *
 */
@XmlRootElement(name="addFriendInfo")
public class AddFriendInfo extends BasicInfo {
	private String senderUserName;
	
	/**
	 * Constructs a basic info bean.
	 */
	AddFriendInfo() {
	}

	/**
	 * @return the senderUserName
	 */
	public String getSenderUserName() {
		return senderUserName;
	}

	/**
	 * @param senderUserName the senderUserName to set
	 */
	public void setSenderUserName(String senderUserName) {
		this.senderUserName = senderUserName;
	}

	/** Getter Setters **/
	
}
