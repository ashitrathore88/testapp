package com.emodoki.rest.beans;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "findUsers")
public class FindUsers extends Base {
	private String findWord;
	private String firstName;
	private String lastName;
	
	
	public String getFindWord() {
		return findWord;
	}
	public void setFindWord(String findWord) {
		this.findWord = findWord;
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
	
	public FindUsers(){
		
	}
	public FindUsers(String firstName, String lastName){
		this.firstName = firstName;
		this.lastName = lastName;
	}
}
