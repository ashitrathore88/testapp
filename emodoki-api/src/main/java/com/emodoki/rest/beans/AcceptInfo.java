package com.emodoki.rest.beans;

public class AcceptInfo extends Base{

	private String facebookId;
	private Integer isAccepted;
	private Integer challengeId;
	
	public String getFacebookId() {
		return facebookId;
	}
	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}
	public Integer getIsAccepted() {
		return isAccepted;
	}
	public void setIsAccepted(Integer isAccepted) {
		this.isAccepted = isAccepted;
	}
	public Integer getChallengeId() {
		return challengeId;
	}
	public void setChallengeId(Integer challengeId) {
		this.challengeId = challengeId;
	}
	
}
