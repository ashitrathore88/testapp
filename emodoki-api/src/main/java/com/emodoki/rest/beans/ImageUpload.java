package com.emodoki.rest.beans;

import java.io.File;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "UploadImage")
public class ImageUpload extends Base {
	private String userName;
	private File file;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}

}
