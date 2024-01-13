package com.krishna.marketplace.services.customer;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.krishna.marketplace.dto.AddToCartDto;
import com.krishna.marketplace.dto.OrderDto;
import com.krishna.marketplace.dto.PlaceOrderDto;

public interface CustomerCartService {
    public ResponseEntity<?> addProductToCart(AddToCartDto addToCartDto);

    public OrderDto getCartByUserId(Long userId);

    public OrderDto applyCoupon(Long userId, String code);

    public OrderDto increaseProductQuantity(AddToCartDto addToCartDto);

    public OrderDto decreaseProductQuantity(AddToCartDto addToCartDto);
    
    public OrderDto placeOrder(PlaceOrderDto placeOrderDto);

    public List<OrderDto> getPlacedOrders(Long userId);
}
