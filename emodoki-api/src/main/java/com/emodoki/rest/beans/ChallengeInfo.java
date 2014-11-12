package com.emodoki.rest.beans;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="challenge")
public class ChallengeInfo extends Base{

	private String userId;
	private ArrayList<String> toUser;
	private String message;
	private String imageBanner;
	private String endDate;
	private String startDate;
	private String language;
	private String challengedUserName;
	
	public String getChallengedUserName() {
		return challengedUserName;
	}
	public void setChallengedUserName(String challengedUserName) {
		this.challengedUserName = challengedUserName;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public ArrayList<String> getToUser() {
		return toUser;
	}
	public void setToUser(ArrayList<String> toUser) {
		this.toUser = toUser;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getImageBanner() {
		return imageBanner;
	}
	public void setImageBanner(String imageBanner) {
		this.imageBanner = imageBanner;
	}
	
	
	
}
