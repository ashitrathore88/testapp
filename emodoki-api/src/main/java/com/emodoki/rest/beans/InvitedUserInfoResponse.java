package com.emodoki.rest.beans;

public class InvitedUserInfoResponse {
	private Integer challengeId;
	private String message;
	private String imageUrl;
	private Integer isAccepted;
	
	public Integer getChallengeId() {
		return challengeId;
	}
	public void setChallengeId(Integer challengeId) {
		this.challengeId = challengeId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public Integer getIsAccepted() {
		return isAccepted;
	}
	public void setAccepted(Integer isAccepted) {
		this.isAccepted = isAccepted;
	}
	
	
}
