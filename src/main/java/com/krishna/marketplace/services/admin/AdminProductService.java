package com.krishna.marketplace.services.admin;

import java.io.IOException;
import java.util.List;

import com.krishna.marketplace.dto.ProductDto;

public interface AdminProductService {
	
	public ProductDto createProduct(ProductDto productDto) throws IOException;
	public List<ProductDto> getAllProducts();
	public List<ProductDto> getAllProductsByName(String name);
	public boolean deleteProduct(Long id);
	public ProductDto getProductById(Long productId);
	public ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException;
}
