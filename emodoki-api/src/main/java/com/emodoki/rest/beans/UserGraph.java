package com.emodoki.rest.beans;

import org.codehaus.jackson.annotate.JsonProperty;



public class UserGraph {
	
	private String x;
	
	
	private String y;
	
	@JsonProperty("X")
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	
	@JsonProperty("Y")
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}

}
