package com.app.model.response;

import org.springframework.data.domain.Pageable;

 
public class ModelResponse<T> {
	private T content;
	private long totalElements;
	private Pageable pageable;
	
	
	
	public ModelResponse() {
		 
	}
	public ModelResponse(T content, long totalElements, Pageable pageable) {
		 
		this.content = content;
		this.totalElements = totalElements;
		this.pageable = pageable;
	}
	public T getContent() {
		return content;
	}
	public void setContent(T content) {
		this.content = content;
	}
	public long getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}
	public Pageable getPageable() {
		return pageable;
	}
	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}
	
	
	
}
