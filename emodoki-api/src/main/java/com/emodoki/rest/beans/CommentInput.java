package com.emodoki.rest.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="challengeComment")
public class CommentInput extends Base {

	private String commentText;
	private String userName;
	private Integer challengeId;
	private String proofImage;
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getChallengeId() {
		return challengeId;
	}
	public void setChallengeId(Integer challengeId) {
		this.challengeId = challengeId;
	}
	public String getProofImage() {
		return proofImage;
	}
	public void setProofImage(String proofImage) {
		this.proofImage = proofImage;
	}
	
	
	
}
