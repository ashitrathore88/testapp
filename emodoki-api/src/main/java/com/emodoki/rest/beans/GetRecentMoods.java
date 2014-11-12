package com.emodoki.rest.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "listMoods")
public class GetRecentMoods {

	@XmlTransient
	private String userName;

	@XmlTransient
	private String email;
	
	private List<RecentMood> mood;
	
	private List<FriendsMoods> friendsMoods;
	
    
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<RecentMood> getMood() {
		return mood;
	}

	public void setMood(List<RecentMood> mood) {
		this.mood = mood;
	}

	public List<FriendsMoods> getFriendsMoods() {
		return friendsMoods;
	}

	public void setFriendsMoods(List<FriendsMoods> friendsMoods) {
		this.friendsMoods = friendsMoods;
	}

	
	
}
