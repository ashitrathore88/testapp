package com.emodoki.rest.beans;

import javax.xml.bind.annotation.XmlTransient;

public class WitnessResponse {
private String message;
private Integer challengeId;
private String challengedUserId;
@XmlTransient
private Integer proofId;

public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public Integer getChallengeId() {
	return challengeId;
}
public void setChallengeId(Integer challengeId) {
	this.challengeId = challengeId;
}

public String getChallengedUserId() {
	return challengedUserId;
}
public void setChallengedUserId(String challengedUserId) {
	this.challengedUserId = challengedUserId;
}
public Integer getProofId() {
	return proofId;
}
public void setProofId(Integer proofId) {
	this.proofId = proofId;
}

}
