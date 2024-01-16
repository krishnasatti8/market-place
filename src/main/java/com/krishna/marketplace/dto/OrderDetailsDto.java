package com.krishna.marketplace.dto;

import java.util.List;

public class OrderDetailsDto {
	
	private List<ProductDto> productDtos;
	private Long orderAmount;
	
	public List<ProductDto> getProductDtos() {
		return productDtos;
	}
	public void setProductDtos(List<ProductDto> productDtos) {
		this.productDtos = productDtos;
	}
	public Long getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Long orderAmount) {
		this.orderAmount = orderAmount;
	}
	
	

}
