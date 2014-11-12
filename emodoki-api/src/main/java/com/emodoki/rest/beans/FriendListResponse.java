package com.emodoki.rest.beans;

import java.util.ArrayList;

public class FriendListResponse {
	private String message;
	private int status;
	private ArrayList<String> emailList;
	private ArrayList<String> contactList;
	
	public ArrayList<String> getEmailList() {
		return emailList;
	}
	public void setEmailList(ArrayList<String> emailList) {
		this.emailList = emailList;
	}
	public ArrayList<String> getContactList() {
		return contactList;
	}
	public void setContactList(ArrayList<String> contactList) {
		this.contactList = contactList;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
