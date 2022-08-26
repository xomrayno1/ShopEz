package com.app.model.request;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.app.model.Voucher;

public class CreateInvoiceRequest {
	private Long userId;
	private BigDecimal amount; //thanh tien 
	//info
	
	private String phone;
	private String name;
	private String province;
	private String district;
	private String ward;
	private String street;
	private Integer payment;
	private String voucherCode;

	private List<CreateInvoiceDetailRequest> details;
 
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public List<CreateInvoiceDetailRequest> getDetails() {
		return details;
	}

	public void setDetails(List<CreateInvoiceDetailRequest> details) {
		this.details = details;
	}

	public String getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}

	public Integer getPayment() {
		return payment;
	}

	public void setPayment(Integer payment) {
		this.payment = payment;
	}

 
 
}
