package com.emodoki.rest.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.emodoki.model.UserMood;

@XmlRootElement(name="recentMoods")
public class GetUserMoods{
	@XmlTransient
	private String userName;
	private List<UserMood> userMoods;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<UserMood> getUserMoods() {
		return userMoods;
	}

	public void setUserMoods(List<UserMood> userMoods) {
		this.userMoods = userMoods;
	}
}