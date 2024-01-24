package com.krishna.marketplace.controller.customer;

import java.util.List;

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
import com.krishna.marketplace.dto.PlaceOrderDto;
import com.krishna.marketplace.exceptions.ValidationException;
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

	@GetMapping("applycoupon/{userId}/{code}")
	public ResponseEntity<?> applyCoupon(@PathVariable Long userId, @PathVariable String code) {
		try {
			OrderDto orderDto = customerCartService.applyCoupon(userId, code);
			return ResponseEntity.status(HttpStatus.OK).body(orderDto);
		} catch (ValidationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("removecoupon/{userId}")
	public ResponseEntity<?> removeCoupon(@PathVariable Long userId) {
		OrderDto orderDto = customerCartService.removeCoupon(userId);
		return ResponseEntity.status(HttpStatus.OK).body(orderDto);
	}

	@PostMapping("/addition")
	public ResponseEntity<?> increaseProductQuantity(@RequestBody AddToCartDto addToCartDto) {
		OrderDto orderDto = customerCartService.increaseProductQuantity(addToCartDto);
		return ResponseEntity.status(HttpStatus.OK).body(orderDto);
	}

	@PostMapping("/deduction")
	public ResponseEntity<?> decreaseProductQuantity(@RequestBody AddToCartDto addToCartDto) {
		OrderDto orderDto = customerCartService.decreaseProductQuantity(addToCartDto);
		return ResponseEntity.status(HttpStatus.OK).body(orderDto);
	}

	@PostMapping("/removefromcart")
	public ResponseEntity<?> removeFromCart(@RequestBody AddToCartDto addToCartDto) {
		OrderDto orderDto = customerCartService.removeProductFromCart(addToCartDto);
		return ResponseEntity.status(HttpStatus.OK).body(orderDto);
	}

	@PostMapping("/placeorder")
	public ResponseEntity<?> placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(customerCartService.placeOrder(placeOrderDto));
	}

	@GetMapping("/myorders/{userId}")
	public ResponseEntity<List<OrderDto>> getPlacedOrders(@PathVariable Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(customerCartService.getPlacedOrders(userId));
	}

	@GetMapping("cartcount/{userId}")
	public ResponseEntity<?> getCartCount(@PathVariable Long userId) {
		return ResponseEntity.status(HttpStatus.OK).body(customerCartService.getCartCount(userId));
	}

}
