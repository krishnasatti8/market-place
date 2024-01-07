package com.krishna.marketplace.services.customer;

import org.springframework.http.ResponseEntity;

import com.krishna.marketplace.dto.AddToCartDto;
import com.krishna.marketplace.dto.OrderDto;

public interface CustomerCartService {
 public ResponseEntity<?> addProductToCart(AddToCartDto addToCartDto);
 public OrderDto getCartByUserId(Long userId);
}
