package com.cartsy.ecom.api.v1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="ecom_users")
@JsonInclude(Include.NON_NULL)
public class EcomUser {
	@Id
	@Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column(name="ecom_username")
	String ecom_username;
	
	@Column(name="ecom_password")
	String ecom_password;
	
	@Column(name="ecom_role")
	String ecom_role;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEcom_username() {
		return ecom_username;
	}

	public void setEcom_username(String ecom_username) {
		this.ecom_username = ecom_username;
	}

	public String getEcom_password() {
		return ecom_password;
	}

	public void setEcom_password(String ecom_password) {
		this.ecom_password = ecom_password;
	}

	public String getEcom_role() {
		return ecom_role;
	}

	public void setEcom_role(String ecom_role) {
		this.ecom_role = ecom_role;
	}


	

}
