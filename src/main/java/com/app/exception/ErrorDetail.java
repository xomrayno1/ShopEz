package com.app.exception;

import java.util.Date;
import java.util.Map;

import com.app.response.APIStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorDetail {
	private Date date;
	private int code;
	private String message;
	private String description;
	private Map<String, String> error;
	
	
	public ErrorDetail(Date date, APIStatus apiStatus, String description ) {
		if(apiStatus == null ) {
			throw new IllegalArgumentException("APIStatus must not be null");
		}
		this.code = apiStatus.getCode();
		this.message = apiStatus.getDescription();
		this.date = date;
		this.description = description;
	}
	public ErrorDetail(Date date, int code, String message, String description) {
		
		this.date = date;
		this.code = code;
		this.message = message;
		this.description = description;
	}
	public ErrorDetail(Date date, int code, String message, String description, Map<String, String> error) {
		
		this.date = date;
		this.code = code;
		this.message = message;
		this.description = description;
		this.error = error;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Map<String, String> getError() {
		return error;
	}
	public void setError(Map<String, String> error) {
		this.error = error;
	}
	
	
	
}
