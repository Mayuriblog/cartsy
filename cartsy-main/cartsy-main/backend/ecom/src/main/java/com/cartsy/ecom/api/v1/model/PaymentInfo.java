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
@Table(name="paymentinfos")
@JsonInclude(Include.NON_NULL)
public class PaymentInfo {
	@Id
	@Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	
	@Column(name="card_name")
	String cardName;
	
	@Column(name="card_no")
	String cardNo;
	
	@Column(name="card_cvv")
	String cardCvv;
	
	@Column(name="card_doe")
	String cardDoe;
	
	@Column(name="ecom_user")
	Integer ecomUserId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardCvv() {
		return cardCvv;
	}

	public void setCardCvv(String cardCvv) {
		this.cardCvv = cardCvv;
	}

	public String getCardDoe() {
		return cardDoe;
	}

	public void setCardDoe(String cardDoe) {
		this.cardDoe = cardDoe;
	}

	public Integer getEcomUserId() {
		return ecomUserId;
	}

	public void setEcomUserId(Integer ecomUserId) {
		this.ecomUserId = ecomUserId;
	}

}
