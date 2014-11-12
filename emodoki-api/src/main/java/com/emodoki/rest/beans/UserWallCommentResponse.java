package com.emodoki.rest.beans;

import java.util.ArrayList;

public class UserWallCommentResponse {
	private Integer commentId;
	private Integer commentCount;
	private ArrayList<String> message;
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public Integer getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	public ArrayList<String> getMessage() {
		return message;
	}
	public void setMessage(ArrayList<String> message) {
		this.message = message;
	}
	
}
