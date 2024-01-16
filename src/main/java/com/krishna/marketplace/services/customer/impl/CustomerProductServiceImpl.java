package com.krishna.marketplace.services.customer.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krishna.marketplace.dto.ProductDetailsDto;
import com.krishna.marketplace.dto.ProductDto;
import com.krishna.marketplace.model.FAQ;
import com.krishna.marketplace.model.Product;
import com.krishna.marketplace.model.Review;
import com.krishna.marketplace.repository.FAQRepository;
import com.krishna.marketplace.repository.ProductRepository;
import com.krishna.marketplace.repository.ReviewRepository;
import com.krishna.marketplace.services.customer.CustomerProductService;

@Service
public class CustomerProductServiceImpl implements CustomerProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private FAQRepository faqRepository;

	public List<ProductDto> getAllProducts() {
		return productRepository.findAll().stream().map(Product::getProductDto).collect(Collectors.toList());
	}

	public List<ProductDto> getAllProductsByName(String name) {
		return productRepository.findAllByNameContaining(name).stream().map(Product::getProductDto)
				.collect(Collectors.toList());
	}

	public ProductDetailsDto getProductDetails(Long id) {
		Optional<Product> optionalProduct = productRepository.findById(id);

		if (optionalProduct.isPresent()) {
			List<FAQ> faqsList = faqRepository.findAllByProductId(id);
			List<Review> reviewsList = reviewRepository.findAllByProductId(id);

			ProductDetailsDto productDetailsDto = new ProductDetailsDto();

			productDetailsDto.setProductDto(optionalProduct.get().getProductDto());
			productDetailsDto.setFaqDtos(faqsList.stream().map(FAQ::getFAQDto).collect(Collectors.toList()));
			productDetailsDto
					.setReviewDtos(reviewsList.stream().map(Review::getReviewDto).collect(Collectors.toList()));
			return productDetailsDto;

		}
		return null;
	}

}
