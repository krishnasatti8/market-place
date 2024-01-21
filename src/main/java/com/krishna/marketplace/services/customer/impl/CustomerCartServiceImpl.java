package com.krishna.marketplace.services.customer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.krishna.marketplace.dto.AddToCartDto;
import com.krishna.marketplace.dto.CartItemDto;
import com.krishna.marketplace.dto.OrderDto;
import com.krishna.marketplace.dto.PlaceOrderDto;
import com.krishna.marketplace.enums.OrderStatus;
import com.krishna.marketplace.exceptions.ValidationException;
import com.krishna.marketplace.model.CartItem;
import com.krishna.marketplace.model.Coupon;
import com.krishna.marketplace.model.Order;
import com.krishna.marketplace.model.Product;
import com.krishna.marketplace.model.User;
import com.krishna.marketplace.repository.CartItemRepository;
import com.krishna.marketplace.repository.CouponRepository;
import com.krishna.marketplace.repository.OrderRepository;
import com.krishna.marketplace.repository.ProductRepository;
import com.krishna.marketplace.repository.UserRepository;
import com.krishna.marketplace.services.customer.CustomerCartService;

import java.util.Date;
import java.util.List;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerCartServiceImpl implements CustomerCartService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CouponRepository couponRepository;

	@Override
	public ResponseEntity<?> addProductToCart(AddToCartDto addToCartDto) {
		Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addToCartDto.getUserId(), OrderStatus.Pending);
		Optional<CartItem> cartItem = cartItemRepository.findByProductIdAndOrderIdAndUserId(addToCartDto.getProductId(),
				activeOrder.getId(), addToCartDto.getUserId());

		System.out.println(activeOrder.getId());
		System.out.println(activeOrder.getOrderStatus());

		if (cartItem.isPresent()) {

			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);

		} else {
			Optional<Product> product = productRepository.findById(addToCartDto.getProductId());
			Optional<User> user = userRepository.findById(addToCartDto.getUserId());

			if (product.isPresent() && user.isPresent()) {
				CartItem newCartItem = new CartItem();
				newCartItem.setProduct(product.get());
				newCartItem.setPrice(product.get().getPrice());
				newCartItem.setQuantity(1L);
				newCartItem.setUser(user.get());
				newCartItem.setOrder(activeOrder);

				CartItem savedCartItem = cartItemRepository.save(newCartItem);
				activeOrder.setTotalAmount(activeOrder.getTotalAmount() + newCartItem.getPrice());
				activeOrder.setAmount(activeOrder.getAmount() + newCartItem.getPrice());
				activeOrder.getCartItems().add(newCartItem);
				orderRepository.save(activeOrder);
				return ResponseEntity.status(HttpStatus.CREATED).body(newCartItem);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product or User not found");
			}

		}

	}

	public OrderDto getCartByUserId(Long userId) {
		Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
		List<CartItemDto> cartItemDtos = activeOrder.getCartItems().stream().map(CartItem::getCartDto)
				.collect(Collectors.toList());
		OrderDto orderDto = new OrderDto();
		orderDto.setId(activeOrder.getId());
		orderDto.setAmount(activeOrder.getAmount());
		orderDto.setTotalAmount(activeOrder.getTotalAmount());
		orderDto.setDiscount(activeOrder.getDiscount());
		orderDto.setOrderStatus(activeOrder.getOrderStatus());
		orderDto.setCartItemDtos(cartItemDtos);

		if (activeOrder.getCoupon() != null) {
			orderDto.setCouponName(activeOrder.getCoupon().getName());
		}

		return orderDto;

	}

	public OrderDto applyCoupon(Long userId, String code) {
		Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
		Coupon coupon = couponRepository.findByCode(code)
				.orElseThrow(() -> new ValidationException("Coupon not found"));

		if (couponIsExpired(coupon)) {
			throw new ValidationException("Coupon is expired");
		}

		double discountAmount = ((coupon.getDiscount() / 100.0) * activeOrder.getTotalAmount());
		double netAmount = activeOrder.getTotalAmount() - discountAmount;

		activeOrder.setAmount((long) netAmount);
		activeOrder.setDiscount((long) discountAmount);
		activeOrder.setCoupon(coupon);

		orderRepository.save(activeOrder);
		return activeOrder.getOrderDto();

	}

	private boolean couponIsExpired(Coupon coupon) {
		Date currentDate = new Date();
		Date expiryDate = coupon.getExpiryDate();
		return expiryDate != null && currentDate.after(expiryDate);
	}

	public OrderDto increaseProductQuantity(AddToCartDto addToCartDto) {

		Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addToCartDto.getUserId(), OrderStatus.Pending);
		Optional<Product> optionalProduct = productRepository.findById(addToCartDto.getProductId());

		Optional<CartItem> optionalCartItem = cartItemRepository.findByProductIdAndOrderIdAndUserId(
				addToCartDto.getProductId(), activeOrder.getId(), addToCartDto.getUserId());

		if (optionalCartItem.isPresent() && optionalProduct.isPresent()) {
			CartItem cartItem = optionalCartItem.get();
			Product product = optionalProduct.get();

			activeOrder.setAmount(activeOrder.getAmount() + product.getPrice());
			activeOrder.setTotalAmount(activeOrder.getTotalAmount() + product.getPrice());
			cartItem.setQuantity(cartItem.getQuantity() + 1);

			if (activeOrder.getCoupon() != null) {
				double discountAmount = ((activeOrder.getCoupon().getDiscount() / 100.0)
						* activeOrder.getTotalAmount());
				double netAmount = activeOrder.getTotalAmount() - discountAmount;
				activeOrder.setAmount((long) netAmount);
				activeOrder.setDiscount((long) discountAmount);
			}

			cartItemRepository.save(cartItem);
			orderRepository.save(activeOrder);
			return activeOrder.getOrderDto();
		} else {
			return null;
		}

	}

	public OrderDto decreaseProductQuantity(AddToCartDto addToCartDto) {

		Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addToCartDto.getUserId(), OrderStatus.Pending);
		Optional<Product> optionalProduct = productRepository.findById(addToCartDto.getProductId());

		Optional<CartItem> optionalCartItem = cartItemRepository.findByProductIdAndOrderIdAndUserId(
				addToCartDto.getProductId(), activeOrder.getId(), addToCartDto.getUserId());

		if (optionalCartItem.isPresent() && optionalProduct.isPresent()) {
			CartItem cartItem = optionalCartItem.get();
			Product product = optionalProduct.get();

			activeOrder.setAmount(activeOrder.getAmount() - product.getPrice());
			activeOrder.setTotalAmount(activeOrder.getTotalAmount() - product.getPrice());
			cartItem.setQuantity(cartItem.getQuantity() - 1);

			if (activeOrder.getCoupon() != null) {
				double discountAmount = ((activeOrder.getCoupon().getDiscount() / 100.0)
						* activeOrder.getTotalAmount());
				double netAmount = activeOrder.getTotalAmount() - discountAmount;
				activeOrder.setAmount((long) netAmount);
				activeOrder.setDiscount((long) discountAmount);
			}

			cartItemRepository.save(cartItem);
			orderRepository.save(activeOrder);
			return activeOrder.getOrderDto();
		} else {
			return null;
		}

	}

	public OrderDto placeOrder(PlaceOrderDto placeOrderDto) {
		Order activeOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.Pending);
		Optional<User> optionalUser = userRepository.findById(placeOrderDto.getUserId());

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
			activeOrder.setAddress(placeOrderDto.getAddress());
			activeOrder.setDate(new Date());
			activeOrder.setOrderStatus(OrderStatus.Placed);
			activeOrder.setTrackingId(UUID.randomUUID());

			orderRepository.save(activeOrder);

			Order order = new Order();
			order.setAmount(0L);
			order.setTotalAmount(0L);
			order.setDiscount(0L);
			order.setOrderStatus(OrderStatus.Pending);
			order.setUser(optionalUser.get());

			orderRepository.save(order);

			return activeOrder.getOrderDto();
		} else {
			return null;

		}

	}

	public List<OrderDto> getPlacedOrders(Long userId) {
		return orderRepository
				.findByUserIdAndOrderStatusIn(userId,
						List.of(OrderStatus.Placed, OrderStatus.Shipped, OrderStatus.Delivered))
				.stream().map(Order::getOrderDto).collect(Collectors.toList());

	}


	public OrderDto searchOrderByTrackingId(UUID trackingId) {
		Optional<Order> optionalOrder = orderRepository.findByTrackingId(trackingId);
		if (optionalOrder.isPresent()) {
			return optionalOrder.get().getOrderDto();
		} else {
			return null;
		}
	}

}
