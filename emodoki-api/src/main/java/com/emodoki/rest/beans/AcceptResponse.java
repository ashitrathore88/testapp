package com.emodoki.rest.beans;

public class AcceptResponse {
private String message;
private int status;
private Integer isAccept;
private String bannerUrl;
private int challengeId;


public int getChallengeId() {
	return challengeId;
}
public void setChallengeId(int challengeId) {
	this.challengeId = challengeId;
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
public Integer getIsAccept() {
	return isAccept;
}
public void setIsAccept(Integer isAccept) {
	this.isAccept = isAccept;
}
public String getBannerUrl() {
	return bannerUrl;
}
public void setBannerUrl(String bannerUrl) {
	this.bannerUrl = bannerUrl;
}


}
