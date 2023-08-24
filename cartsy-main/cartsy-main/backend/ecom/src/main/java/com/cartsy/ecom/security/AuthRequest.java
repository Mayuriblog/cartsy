package com.cartsy.ecom.security;

import javax.persistence.Column;

public class AuthRequest {
	@Column(name="username")
	String username;
	
	@Column(name="password")
	String password;

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

}
