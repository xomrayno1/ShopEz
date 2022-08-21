package com.app.response;

import java.io.Serializable;
 
public class APIResponse<T> implements Serializable {
	private int code;
	private String message;
	private T data;
	
	public APIResponse() {}
	public APIResponse(APIStatus apiStatus, T data) {
		if(apiStatus == null) {
			throw new IllegalArgumentException("APIStatus must not be null");
		}
		this.code = apiStatus.getCode();
		this.message = apiStatus.getDescription();
		this.data = data;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	
}
