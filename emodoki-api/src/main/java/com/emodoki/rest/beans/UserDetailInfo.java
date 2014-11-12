package com.emodoki.rest.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserDetailInfo extends Base{
	private String facebookId;
	private Integer challengeId;

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public Integer getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(Integer challengeId) {
		this.challengeId = challengeId;
	}


}
