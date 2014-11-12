package com.emodoki.rest.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RecentMood {

	private String moodDescription;
	private String moodImage;
	private String latitude;
	private String longitude;
	private String timeAgo;
	private String mood;
	private String createdOn;
	private String imageURL;
	
	
	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getTimeAgo() {
		return timeAgo;
	}

	public void setTimeAgo(String timeAgo) {
		this.timeAgo = timeAgo;
	}

	public String getMood() {
		return mood;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

	public String getMoodDescription() {
		return moodDescription;
	}

	public void setMoodDescription(String moodDescription) {
		this.moodDescription = moodDescription;
	}

	public String getMoodImage() {
		return moodImage;
	}

	public void setMoodImage(String moodImage) {
		this.moodImage = moodImage;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the imageURL
	 */
	public String getImageURL() {
		return imageURL;
	}

	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

}
