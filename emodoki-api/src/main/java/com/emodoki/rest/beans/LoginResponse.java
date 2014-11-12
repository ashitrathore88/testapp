package com.emodoki.rest.beans;



public class LoginResponse {
private String message;
private int status;
private Integer isNewUser;
private String email;

private String userId;

public LoginResponse(){
}

public LoginResponse(String message,int status){
	this.message = message;
	this.status = status;
}

public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}

public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}

public Integer getIsNewUser() {
	return isNewUser;
}

public void setIsNewUser(Integer isNewUser) {
	this.isNewUser = isNewUser;
}



}
