package com.krishna.marketplace.dto;

import java.util.List;

public class ProductDetailsDto {

	private ProductDto productDto;

	private List<ReviewDto> reviewDtos;

	private List<FAQDto> faqDtos;

	public ProductDto getProductDto() {
		return productDto;
	}

	public void setProductDto(ProductDto productDto) {
		this.productDto = productDto;
	}

	public List<ReviewDto> getReviewDtos() {
		return reviewDtos;
	}

	public void setReviewDtos(List<ReviewDto> reviewDtos) {
		this.reviewDtos = reviewDtos;
	}

	public List<FAQDto> getFaqDtos() {
		return faqDtos;
	}

	public void setFaqDtos(List<FAQDto> faqDtos) {
		this.faqDtos = faqDtos;
	}

}
