package com.emodoki.rest.beans;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "account")
public class AccountInfo extends Base {

	private String username;
	@XmlTransient
	private String password;
	@XmlTransient
	private String about;
	private String firstName;
	private String lastName;
	@XmlTransient
	private String dob;
	private String country;
	private String email;
	@XmlTransient
	private String gender;
	@XmlTransient
	private String mood;
	@XmlTransient
	private String locale;
	@XmlTransient
	private String currentPassword;
	@XmlTransient
	private List<CommentInfo> comments;
	@XmlTransient
	private int friendCount;
	@XmlTransient
	private String imageUrl;
	@XmlTransient
	private String whyMood;
	@XmlTransient
	private String moodImageUrl;
	@XmlTransient
	private Date userCreated;
	@XmlTransient
	private Set<Notification> notification;
	@XmlTransient
	private List<FindUsers> findUsers;
	@XmlTransient
	private int userMoodId;
	@XmlTransient
	private Integer displayMood = 10;
	@XmlTransient
	private String lastLogin;
	@XmlTransient
	private String status;
	@XmlTransient
	private String latitude;
	@XmlTransient
	private String longitude;
	@XmlTransient
	private String contact;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public int getUserMoodId() {
		return userMoodId;
	}

	public void setUserMoodId(int userMoodId) {
		this.userMoodId = userMoodId;
	}

	public Date getUserCreated() {
		return userCreated;
	}

	public void setUserCreated(Date userCreated) {
		this.userCreated = userCreated;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public AccountInfo(String username, String password, String firstName,
			String lastName) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public AccountInfo(String username, String firstName, String lastName) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public AccountInfo() {
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getMood() {
		return mood;
	}

	public void setMood(String mood) {
		this.mood = mood;
	}

	public int getFriendCount() {
		return friendCount;
	}

	public void setFriendCount(int friendCount) {
		this.friendCount = friendCount;
	}

	public String getWhyMood() {
		return whyMood;
	}

	public void setWhyMood(String whyMood) {
		this.whyMood = whyMood;
	}

	public String getMoodImageUrl() {
		return moodImageUrl;
	}

	public void setMoodImageUrl(String moodImageUrl) {
		this.moodImageUrl = moodImageUrl;
	}

	public Set<Notification> getNotification() {
		return notification;
	}

	public void setNotification(Set<Notification> notification) {
		this.notification = notification;
	}

	public List<FindUsers> getFindUsers() {
		return findUsers;
	}

	public void setFindUsers(List<FindUsers> findUsers) {
		this.findUsers = findUsers;
	}

	public Integer getDisplayMood() {
		return displayMood;
	}

	public void setDisplayMood(Integer displayMood) {
		this.displayMood = displayMood;
	}

	public List<CommentInfo> getComments() {
		return comments;
	}

	public void setComments(List<CommentInfo> comments) {
		this.comments = comments;
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

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	

}
