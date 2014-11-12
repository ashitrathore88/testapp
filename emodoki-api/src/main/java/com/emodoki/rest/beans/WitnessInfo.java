package com.emodoki.rest.beans;

import java.util.ArrayList;

public class WitnessInfo extends Base{
	
private Integer challengeId;
private ArrayList<WitnessName> witnessFbId;
private String challengedUserId;

public Integer getChallengeId() {
	return challengeId;
}
public void setChallengeId(Integer challengeId) {
	this.challengeId = challengeId;
}
/*public ArrayList<String> getWitnessFbId() {
	return witnessFbId;
}
public void setWitnessFbId(ArrayList<String> witnessFbId) {
	this.witnessFbId = witnessFbId;
}*/


public String getChallengedUserId() {
	return challengedUserId;
}
public ArrayList<WitnessName> getWitnessFbId() {
	return witnessFbId;
}
public void setWitnessFbId(ArrayList<WitnessName> witnessFbId) {
	this.witnessFbId = witnessFbId;
}
public void setChallengedUserId(String challengedUserId) {
	this.challengedUserId = challengedUserId;
}


}
