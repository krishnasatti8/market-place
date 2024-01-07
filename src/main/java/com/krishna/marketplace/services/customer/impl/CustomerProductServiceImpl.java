package com.krishna.marketplace.services.customer.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.krishna.marketplace.dto.ProductDto;
import com.krishna.marketplace.model.Product;
import com.krishna.marketplace.repository.ProductRepository;
import com.krishna.marketplace.services.customer.CustomerProductService;

@Service
public class CustomerProductServiceImpl implements CustomerProductService {

	@Autowired
	private ProductRepository productRepository;

	public List<ProductDto> getAllProducts() {
		return productRepository.findAll().stream().map(Product::getProductDto).collect(Collectors.toList());
	}

	public List<ProductDto> getAllProductsByName(String name) {
		return productRepository.findAllByNameContaining(name).stream().map(Product::getProductDto)
				.collect(Collectors.toList());
	}

}
