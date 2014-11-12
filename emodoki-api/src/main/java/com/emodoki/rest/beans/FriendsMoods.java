package com.emodoki.rest.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "friendsMoods")
public class FriendsMoods {

	@XmlTransient
	private String friendUsername;

	@XmlTransient
	private String latitude;
	@XmlTransient
	private String longitude;

	private List<RecentMood> moods;

	public String getFriendUsername() {
		return friendUsername;
	}

	public void setFriendUsername(String friendUsername) {
		this.friendUsername = friendUsername;
	}

	public List<RecentMood> getMoods() {
		return moods;
	}

	public void setMoods(List<RecentMood> moods) {
		this.moods = moods;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	
}
