/**
 * 
 */
package com.app.model.request;
 
public class CreateVoucherRequest {
	private String name;
	private Integer discount;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
}
