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
@Table(name="reviews")
@JsonInclude(Include.NON_NULL)
public class Review {
	
	@Id
	@Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column(name="ecom_user")
	Integer ecomUser;
	
	@Column(name="ecom_username")
	String ecomUsername;
	
	@Column(name="product_id")
	Integer productId;
	
	@Column(name="review_details")
	String reviewDetails;
	
	@Column(name="review_rating")
	Integer reviewRating;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEcomUser() {
		return ecomUser;
	}

	public void setEcomUser(Integer ecomUser) {
		this.ecomUser = ecomUser;
	}

	public String getEcomUsername() {
		return ecomUsername;
	}

	public void setEcomUsername(String ecomUsername) {
		this.ecomUsername = ecomUsername;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getReviewDetails() {
		return reviewDetails;
	}

	public void setReviewDetails(String reviewDetails) {
		this.reviewDetails = reviewDetails;
	}

	public Integer getReviewRating() {
		return reviewRating;
	}

	public void setReviewRating(Integer reviewRating) {
		this.reviewRating = reviewRating;
	}

	
	

}
