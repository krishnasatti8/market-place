package com.krishna.marketplace.controller.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krishna.marketplace.dto.ProductDetailsDto;
import com.krishna.marketplace.dto.ProductDto;
import com.krishna.marketplace.services.customer.CustomerProductService;

@RestController
@RequestMapping("/api/customer")
public class CustomerProductController {

	@Autowired
	private CustomerProductService productService;

	@GetMapping("/products")
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		return ResponseEntity.ok(productService.getAllProducts());
	}

	@GetMapping("/search/{name}")
	public ResponseEntity<List<ProductDto>> getAllProductsByName(@PathVariable String name) {
		return ResponseEntity.ok(productService.getAllProductsByName(name));
	}

	@GetMapping("/productdetails/{productId}")
	public ResponseEntity<ProductDetailsDto> getProductDetails(@PathVariable Long productId) {
		ProductDetailsDto productDetailsDto = productService.getProductDetails(productId);
		if (productDetailsDto != null) {
			return ResponseEntity.ok(productDetailsDto);
		}
		return ResponseEntity.notFound().build();
	}

}
