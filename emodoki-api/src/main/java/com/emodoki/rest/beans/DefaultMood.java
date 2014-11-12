package com.emodoki.rest.beans;


import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="defaultmood")
public class DefaultMood extends Base {
	
	private String username;
	private String mood;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMood() {
		return mood;
	}
	public void setMood(String mood) {
		this.mood = mood;
	}
	
	public DefaultMood(){
		
	}
	public DefaultMood(String username, String mood){
		this.username = username;
		this.mood = mood;
	}

}
