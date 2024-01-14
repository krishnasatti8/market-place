package com.krishna.marketplace.services.admin.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krishna.marketplace.dto.ProductDto;
import com.krishna.marketplace.model.Category;
import com.krishna.marketplace.model.Product;
import com.krishna.marketplace.repository.CategoryRespository;
import com.krishna.marketplace.repository.ProductRepository;
import com.krishna.marketplace.services.admin.AdminProductService;

@Service
public class AdminProductServiceImpl implements AdminProductService {

    @Autowired
    private CategoryRespository categoryRespository;

    @Autowired
    private ProductRepository productRepository;

    public ProductDto createProduct(ProductDto productDto) throws IOException {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImage(productDto.getImage().getBytes());

        Category category = categoryRespository.findById(productDto.getCategoryId()).orElseThrow();
        product.setCategory(category);
        return productRepository.save(product).getProductDto();
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(Product::getProductDto).collect(Collectors.toList());
    }

    public List<ProductDto> getAllProductsByName(String name) {
        return productRepository.findAllByNameContaining(name).stream().map(Product::getProductDto)
                .collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ProductDto getProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            return product.get().getProductDto();
        }
        return null;
    }

    public ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<Category> optionalCategory = categoryRespository.findById(productDto.getCategoryId());
        if (optionalProduct.isPresent() && optionalCategory.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            product.setCategory(optionalCategory.get());
            if (productDto.getImage() != null) {
                product.setImage(productDto.getImage().getBytes());
            }
            return productRepository.save(product).getProductDto();
        }
        return null;

    }

}
