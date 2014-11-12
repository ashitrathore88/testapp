package com.emodoki.rest.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "findFriends")
public class FindFriends extends Base {
	private String searchFriends;

	public String getSearchFriends() {
		return searchFriends;
	}

	public void setSearchFriends(String searchFriends) {
		this.searchFriends = searchFriends;
	}

}
