package com.krishna.marketplace.services.customer;

import java.util.List;

import com.krishna.marketplace.dto.ProductDto;

public interface CustomerProductService {

	public List<ProductDto> getAllProducts();

	public List<ProductDto> getAllProductsByName(String name);

}
