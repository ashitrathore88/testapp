package com.emodoki.rest.beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserWallResponse implements Comparable<UserWallResponse> {
	private Integer challengeId;
	private String ChallengeCreatedBy;
	private String message;
	private String imageUrl;
	private Integer invitedUsers;
	private String createdDate;
	private String expireDate;
	private ArrayList<ChallengeUserInfo> challengeUserInfo;
	private String challengeType;
	private Integer isAccept;
	private Integer status;
	private String statusDescription;
	private ArrayList<CommentResponse> commentResponse;
	private ArrayList<WitnessInfoResponse> witnessResponse;

	public ArrayList<ChallengeUserInfo> getChallengeUserInfo() {
		return challengeUserInfo;
	}

	public void setChallengeUserInfo(
			ArrayList<ChallengeUserInfo> challengeUserInfo) {
		this.challengeUserInfo = challengeUserInfo;
	}

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

	public Integer getInvitedUsers() {
		return invitedUsers;
	}

	public void setInvitedUsers(Integer invitedUsers) {
		this.invitedUsers = invitedUsers;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getChallengeType() {
		return challengeType;
	}

	public void setChallengeType(String challengeType) {
		this.challengeType = challengeType;
	}

	public Integer getIsAccept() {
		return isAccept;
	}

	public void setIsAccept(Integer isAccept) {
		this.isAccept = isAccept;
	}

	public ArrayList<CommentResponse> getCommentResponse() {
		return commentResponse;
	}

	public void setCommentResponse(ArrayList<CommentResponse> commentResponse) {
		this.commentResponse = commentResponse;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public ArrayList<WitnessInfoResponse> getWitnessResponse() {
		return witnessResponse;
	}

	public void setWitnessResponse(
			ArrayList<WitnessInfoResponse> witnessResponse) {
		this.witnessResponse = witnessResponse;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getChallengeCreatedBy() {
		return ChallengeCreatedBy;
	}

	public void setChallengeCreatedBy(String challengeCreatedBy) {
		ChallengeCreatedBy = challengeCreatedBy;
	}

	@Override
	public int compareTo(UserWallResponse o) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = null;
		Date date1 = null;
		try {
			date = formatter.parse(this.getCreatedDate());
			date1 = formatter.parse(o.getCreatedDate());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date.compareTo(date1) < 0 ? 1 : -1;
	}
}
