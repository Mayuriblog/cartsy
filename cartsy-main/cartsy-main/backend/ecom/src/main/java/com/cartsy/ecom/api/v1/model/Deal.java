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
@Table(name="deals")
@JsonInclude(Include.NON_NULL)
public class Deal {
	
	@Id
	@Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column(name = "deal_name")
    String dealName;
	
	@Column(name = "deal_desc")
    String dealDesc;
	
	@Column(name = "deal_url")
    String dealUrl;
	
	@Column(name = "deal_image")
    String dealImage;
	
	@Column(name = "deal_category")
    String dealCategory;
	
	@Column(name = "deal_start_date")
    Date dealStartDate;
	
	@Column(name = "deal_end_date")
    Date dealEndDate;
	
	@Column(name = "deal_products")
    String dealProducts;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDealName() {
		return dealName;
	}

	public void setDealName(String dealName) {
		this.dealName = dealName;
	}

	public String getDealDesc() {
		return dealDesc;
	}

	public void setDealDesc(String dealDesc) {
		this.dealDesc = dealDesc;
	}

	public String getDealUrl() {
		return dealUrl;
	}

	public void setDealUrl(String dealUrl) {
		this.dealUrl = dealUrl;
	}

	public String getDealImage() {
		return dealImage;
	}

	public void setDealImage(String dealImage) {
		this.dealImage = dealImage;
	}

	public String getDealCategory() {
		return dealCategory;
	}

	public void setDealCategory(String dealCategory) {
		this.dealCategory = dealCategory;
	}

	public Date getDealStartDate() {
		return dealStartDate;
	}

	public void setDealStartDate(Date dealStartDate) {
		this.dealStartDate = dealStartDate;
	}

	public Date getDealEndDate() {
		return dealEndDate;
	}

	public void setDealEndDate(Date dealEndDate) {
		this.dealEndDate = dealEndDate;
	}

	public String getDealProducts() {
		return dealProducts;
	}

	public void setDealProducts(String dealProducts) {
		this.dealProducts = dealProducts;
	}

	


}
