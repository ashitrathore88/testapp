package com.emodoki.rest.beans;

public class InvitedUserResponse {
private Integer challengeId;
private boolean isAccepted;
public Integer getChallengeId() {
	return challengeId;
}
public void setChallengeId(Integer challengeId) {
	this.challengeId = challengeId;
}
public boolean isAccepted() {
	return isAccepted;
}
public void setAccepted(boolean isAccepted) {
	this.isAccepted = isAccepted;
}

}
