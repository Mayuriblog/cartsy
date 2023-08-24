package com.cartsy.ecom.api.v1.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class NewUser {
	
	@JsonProperty("username")
	String username;
	
	@JsonProperty("password")
	String password;

	@JsonProperty("email")
	String email;
	
	@JsonProperty("type")
	String type;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
