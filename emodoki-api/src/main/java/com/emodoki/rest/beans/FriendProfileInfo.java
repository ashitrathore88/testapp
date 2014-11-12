package com.emodoki.rest.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class containing the basic info of the user.
 * @author Nikhil
 *
 */
@XmlRootElement(name="friendProfileInfo")
public class FriendProfileInfo extends Base {
	private String userName;
	private String friendName;
	
	/**
	 * 
	 * 
	 */
	public FriendProfileInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/** Getter Setters **/
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFriendName() {
		return friendName;
	}

	public void setFriendName(String friendName) {
		this.friendName = friendName;
	}
	
	
}
