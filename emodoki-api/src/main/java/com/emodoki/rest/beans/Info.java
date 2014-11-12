package com.emodoki.rest.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * This is bean is particularly used for login.
 * @author Sunny
 *
 */
@XmlRootElement(name = "info")
public class Info extends Base {
	private String value;
	private String username;
	private String mood;
	private Integer moodId;
	private String why;
	private String comment;
	private String from;
	private String to;
	private String latitude;
	private String longitude;
	private Integer userMoodId;
	

	@XmlElement()
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	@XmlElement(nillable=true)
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	@XmlElement(nillable=true)
	public String getMood() {
		return mood;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}
	@XmlElement(nillable=true)
	public Integer getMoodId() {
		return moodId;
	}

	public void setMoodId(Integer moodId) {
		this.moodId = moodId;
	}
	@XmlElement(nillable=true)
	public String getWhy() {
		return why;
	}
	@XmlElement(nillable=true)
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	@XmlElement(nillable=true)
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
	@XmlElement(nillable=true)
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setWhy(String why) {
		this.why = why;
	}
	
	public Integer getUserMoodId() {
		return userMoodId;
	}

	public void setUserMoodId(Integer userMoodId) {
		this.userMoodId = userMoodId;
	}

	public Info(String value) {
		super();
		this.value = value;
	}
	
	
	@XmlElement(nillable=true)
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@XmlElement(nillable=true)
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Info() {
		
	}

	
}
