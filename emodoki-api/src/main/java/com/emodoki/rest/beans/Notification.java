package com.emodoki.rest.beans;




public class Notification {
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String imageUrl;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Notification(){
		
	}
	public Notification( String userName, String firstName, String lastName, String email){
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}