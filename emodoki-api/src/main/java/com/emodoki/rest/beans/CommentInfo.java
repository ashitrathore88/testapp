package com.emodoki.rest.beans;


import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="comments")
public class CommentInfo extends Base {
	
	private String text;
	private String created;
	private String firstName;
	private String lastName;
	private String imageUrl;
	@XmlTransient
	private String userName;

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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	/** Constructor **/
	public CommentInfo(String text, String created, String firstName, String lastName, String imageUrl, String userName) {
		super();
		this.text = text;
		this.userName = userName;
		this.created = created;
		this.firstName = firstName;
		this.lastName = lastName;
		this.imageUrl = imageUrl;
	}
	
	public CommentInfo() {
		super();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
