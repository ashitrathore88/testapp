package com.emodoki.rest.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="friends")
public class FriendsInfo extends Base {
	private String userName;
	private long friendsCount;
	private List<FriendInfo> friendsInfo;
	
	
	/*** Getters Setter ***/
	public long getFriendsCount() {
		return friendsCount;
	}
	public void setFriendsCount(long friendsCount) {
		this.friendsCount = friendsCount;
	}
	public List<FriendInfo> getFriendsInfo() {
		return friendsInfo;
	}
	public void setFriendsInfo(List<FriendInfo> friendsInfo) {
		this.friendsInfo = friendsInfo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/*** Constructors ***/
	public FriendsInfo() {
		super();
	}
	
	public FriendsInfo(String userName, long friendsCount,
			List<FriendInfo> friendsInfo) {
		super();
		this.userName = userName;
		this.friendsCount = friendsCount;
		this.friendsInfo = friendsInfo;
	}

	
	
}
