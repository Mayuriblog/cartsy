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
@Table(name="addresses")
@JsonInclude(Include.NON_NULL)
public class Address {
	@Id
	@Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column(name="hno")
	Integer hNo;
	
	@Column(name="line1")
	String line1;
	
	@Column(name="line2")
	String line2;
	
	@Column(name="city")
	String city;
	
	@Column(name="address_state")
	String state;
	
	@Column(name="country")
	String country;
	
	@Column(name="pincode")
	String pincode;
	
	@Column(name="phone")
	String phone;
	
	@Column(name="ecom_user")
	Integer ecomUserId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer gethNo() {
		return hNo;
	}

	public void sethNo(Integer hNo) {
		this.hNo = hNo;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getEcomUserId() {
		return ecomUserId;
	}

	public void setEcomUserId(Integer ecomUserId) {
		this.ecomUserId = ecomUserId;
	}

}
