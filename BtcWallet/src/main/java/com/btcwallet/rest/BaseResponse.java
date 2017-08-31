package com.btcwallet.rest;

import com.btcwallet.enums.Status;



public class BaseResponse {

	private Object data;
	
	private Status status;
	
	private String message;
	
	public BaseResponse(Status status, String message, Object data) {
		this.data = data;
		this.message = message;
		this.status = status;
	}

	public static BaseResponse success(String message) {
		return new BaseResponse(Status.SUCCESS,message,null);
	}

	public static BaseResponse error(String message) {
		return new BaseResponse(Status.ERROR,message,null);
	}


	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
