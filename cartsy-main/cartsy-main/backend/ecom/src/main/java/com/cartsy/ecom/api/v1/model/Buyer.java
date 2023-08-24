package com.cartsy.ecom.api.v1.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="buyers")
@JsonInclude(Include.NON_NULL)
public class Buyer {
	
	@Id
	@Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column(name="buyer_name")
	String buyerName;
	
	@Column(name="buyer_phone")
	String buyerPhone;
	
	@Column(name="buyer_email")
	String buyerEmail;
	
	@Column(name="buyer_payment_options")
	String buyerPaymentOptions;
	
	@Column(name="buyer_addresses")
	String buyerAddresses;
	
	@Column(name="buyer_doj")
	Date buyerDoj;
	
	@Column(name="buyer_status")
	String buyerStatus;
	
	@Column(name="buyer_rating")
	String buyerRating;
	
	@Column(name="buyer_profile_pic")
	String buyerProfilePic;
	
	@Column(name="ecom_user")
	Integer ecomUserId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerPhone() {
		return buyerPhone;
	}

	public void setBuyerPhone(String buyerPhone) {
		this.buyerPhone = buyerPhone;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getBuyerPaymentOptions() {
		return buyerPaymentOptions;
	}

	public void setBuyerPaymentOptions(String buyerPaymentOptions) {
		this.buyerPaymentOptions = buyerPaymentOptions;
	}

	public String getBuyerAddresses() {
		return buyerAddresses;
	}

	public void setBuyerAddresses(String buyerAddresses) {
		this.buyerAddresses = buyerAddresses;
	}

	public Date getBuyerDoj() {
		return buyerDoj;
	}

	public void setBuyerDoj(Date buyerDoj) {
		this.buyerDoj = buyerDoj;
	}

	public String getBuyerStatus() {
		return buyerStatus;
	}

	public void setBuyerStatus(String buyerStatus) {
		this.buyerStatus = buyerStatus;
	}

	public String getBuyerRating() {
		return buyerRating;
	}

	public void setBuyerRating(String buyerRating) {
		this.buyerRating = buyerRating;
	}

	public String getBuyerProfilePic() {
		return buyerProfilePic;
	}

	public void setBuyerProfilePic(String buyerProfilePic) {
		this.buyerProfilePic = buyerProfilePic;
	}

	public Integer getEcomUserId() {
		return ecomUserId;
	}

	public void setEcomUserId(Integer ecomUserId) {
		this.ecomUserId = ecomUserId;
	}
	






}
