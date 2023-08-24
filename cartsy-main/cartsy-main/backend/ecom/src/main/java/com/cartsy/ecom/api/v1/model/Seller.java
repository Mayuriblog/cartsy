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
@Table(name="sellers")
@JsonInclude(Include.NON_NULL)
public class Seller {
	
	@Id
	@Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column(name="seller_name")
	String sellerName;
	
	@Column(name="seller_tin")
	String sellerTin;
	
	@Column(name="seller_rating")
	String sellerRating;
	
	@Column(name="seller_address")
	String sellerAddress;
	
	@Column(name="seller_phone")
	String sellerPhone;
	
	@Column(name="seller_email")
	String sellerEmail;

	@Column(name="seller_doj")
	Date sellerDoj;

	@Column(name="seller_status")
	String sellerStatus;
	
	@Column(name="seller_profile_pic")
	String sellerProfilePic;
	
	@Column(name="ecom_user")
	Integer ecomUserId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerTin() {
		return sellerTin;
	}

	public void setSellerTin(String sellerTin) {
		this.sellerTin = sellerTin;
	}

	public String getSellerRating() {
		return sellerRating;
	}

	public void setSellerRating(String sellerRating) {
		this.sellerRating = sellerRating;
	}

	public String getSellerAddress() {
		return sellerAddress;
	}

	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	public String getSellerPhone() {
		return sellerPhone;
	}

	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public Date getSellerDoj() {
		return sellerDoj;
	}

	public void setSellerDoj(Date sellerDoj) {
		this.sellerDoj = sellerDoj;
	}

	public String getSellerStatus() {
		return sellerStatus;
	}

	public void setSellerStatus(String sellerStatus) {
		this.sellerStatus = sellerStatus;
	}

	public String getSellerProfilePic() {
		return sellerProfilePic;
	}

	public void setSellerProfilePic(String sellerProfilePic) {
		this.sellerProfilePic = sellerProfilePic;
	}

	public Integer getEcomUserId() {
		return ecomUserId;
	}

	public void setEcomUserId(Integer ecomUserId) {
		this.ecomUserId = ecomUserId;
	}




 

}
