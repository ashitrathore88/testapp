package com.emodoki.rest.beans;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="commentResponse")
public class CommentResponse {

	private Integer commentId;
	private Integer commentCount;
	private String message;
	@XmlTransient
	private String commentedUser;
	private String proofImageUrl;
	@XmlTransient
	private String createdDate;
	
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCommentedUser() {
		return commentedUser;
	}
	public void setCommentedUser(String commentedUser) {
		this.commentedUser = commentedUser;
	}
	public String getProofImageUrl() {
		return proofImageUrl;
	}
	public void setProofImageUrl(String proofImageUrl) {
		this.proofImageUrl = proofImageUrl;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
}
