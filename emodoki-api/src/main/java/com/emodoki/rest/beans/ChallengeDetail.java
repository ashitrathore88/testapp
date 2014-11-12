package com.emodoki.rest.beans;

import java.util.ArrayList;

public class ChallengeDetail {
	private int challengeId;
	private String createdBy;
	
	private ArrayList<String> toUser;
	private String description;
	private String imageBanner;
	private String endDate;
	private String startDate;
	private String language;
	private Integer status;
	private Integer isAccept;
	private String  challengeType;
	
	
	private ArrayList<CommentResponse> commentResponse = null;
	private ArrayList<WitnessInfoResponse> witnessResponse = null;
	

	
	public String getChallengeType() {
		return challengeType;
	}
	public void setChallengeType(String challengeType) {
		this.challengeType = challengeType;
	}
	
	public ArrayList<CommentResponse> getCommentResponse() {
		return commentResponse;
	}
	public void setCommentResponse(ArrayList<CommentResponse> commentResponse) {
		this.commentResponse = commentResponse;
	}
	public ArrayList<WitnessInfoResponse> getWitnessResponse() {
		return witnessResponse;
	}
	public void setWitnessResponse(ArrayList<WitnessInfoResponse> witnessResponse) {
		this.witnessResponse = witnessResponse;
	}
	public Integer getIsAccept() {
		return isAccept;
	}
	public void setIsAccept(Integer isAccept) {
		this.isAccept = isAccept;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public ArrayList<String> getToUser() {
		return toUser;
	}
	public void setToUser(ArrayList<String> toUser) {
		this.toUser = toUser;
	}
	
	public String getImageBanner() {
		return imageBanner;
	}
	public void setImageBanner(String imageBanner) {
		this.imageBanner = imageBanner;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public int getChallengeId() {
		return challengeId;
	}
	public void setChallengeId(int challengeId) {
		this.challengeId = challengeId;
	}
	

}
