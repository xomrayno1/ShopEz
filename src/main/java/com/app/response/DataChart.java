package com.app.response;

import java.math.BigDecimal;

public class DataChart {
	private int month;
	private BigDecimal price;
	public DataChart( ) {
	
	}
	public DataChart(int month, BigDecimal price) {
		super();
		this.month = month;
		this.price = price;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
 
}
