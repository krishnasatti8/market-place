package com.krishna.marketplace.services.auth.admin;

import java.io.IOException;
import java.util.List;

import com.krishna.marketplace.dto.ProductDto;

public interface ProductService {
	
	public ProductDto createProduct(ProductDto productDto) throws IOException;
	public List<ProductDto> getAllProducts();
	public List<ProductDto> getAllProductsByName(String name);
	public boolean deleteProduct(Long id);
}
