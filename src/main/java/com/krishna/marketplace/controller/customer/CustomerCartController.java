package com.krishna.marketplace.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krishna.marketplace.dto.AddToCartDto;
import com.krishna.marketplace.dto.OrderDto;
import com.krishna.marketplace.services.customer.CustomerCartService;

@RestController
@RequestMapping("/api/customer")
public class CustomerCartController {

	@Autowired
	private CustomerCartService customerCartService;

	@PostMapping("/addtocart")
	public ResponseEntity<?> addToCart(@RequestBody AddToCartDto cartItemDto) {
		return customerCartService.addProductToCart(cartItemDto);
	}

	@GetMapping("/cart/{userId}")
	public ResponseEntity<?> getCart(@PathVariable Long userId) {
		OrderDto orderDto = customerCartService.getCartByUserId(userId);
		return ResponseEntity.status(HttpStatus.OK).body(orderDto);
	}

}
