package com.cartsy.ecom.api.v1.model;


public class RestResponse {
	public RestResponse(int status, String message, String error_code, String error_details) {
		super();
		this.status = status;
		this.message = message;
		this.error_code = error_code;
		this.error_details = error_details;
	}

	int status;
	


	String message = new String();
	
	String error_code = new String();
	
	String error_details = new String();
	
	public int getStatus() {
		
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError_code() {
		return error_code;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public String getError_details() {
		return error_details;
	}

	public void setError_details(String error_details) {
		this.error_details = error_details;
	}

}
