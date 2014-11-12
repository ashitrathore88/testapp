package com.emodoki.rest.beans;


import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "friendInfo")
public class FriendInfo {

	private String userName;
	private String imgPath;
	private String firstName;
	private String lastName;
	private String lastLogin;
	private String email;
	private Boolean privacyStatus;

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public FriendInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FriendInfo(String userName, String imgPth, String firstName, String lastName, String lastLogin, String email, Boolean privacyStatus) {
		super();
		this.userName = userName;
		this.imgPath = imgPth;
		this.firstName = firstName;
		this.lastName = lastName;
		this.lastLogin = lastLogin;
		this.email = email;
		this.privacyStatus = privacyStatus;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the privacyStatus
	 */
	public Boolean getPrivacyStatus() {
		return privacyStatus;
	}

	/**
	 * @param privacyStatus the privacyStatus to set
	 */
	public void setPrivacyStatus(Boolean privacyStatus) {
		this.privacyStatus = privacyStatus;
	}

}
