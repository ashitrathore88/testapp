package com.emodoki.rest.beans;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="friendList")
public class FriendListInfo extends Base{	
private ArrayList<String> fb_id;

public FriendListInfo() {
	super();
}

public ArrayList<String> getFb_id() {
	return fb_id;
}

public void setFb_id(ArrayList<String> fb_id) {
	this.fb_id = fb_id;
}



}
