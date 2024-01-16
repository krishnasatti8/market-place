package com.krishna.marketplace.services.customer.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krishna.marketplace.dto.OrderDetailsDto;
import com.krishna.marketplace.dto.ProductDto;
import com.krishna.marketplace.dto.ReviewDto;
import com.krishna.marketplace.model.CartItem;
import com.krishna.marketplace.model.Order;
import com.krishna.marketplace.model.Product;
import com.krishna.marketplace.model.Review;
import com.krishna.marketplace.model.User;
import com.krishna.marketplace.repository.OrderRepository;
import com.krishna.marketplace.repository.ProductRepository;
import com.krishna.marketplace.repository.ReviewRepository;
import com.krishna.marketplace.repository.UserRepository;
import com.krishna.marketplace.services.customer.CustomerReviewService;

@Service
public class CustomerReviewServiceImpl implements CustomerReviewService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	public OrderDetailsDto getOrderDetails(Long orderId) {
		Optional<Order> optionalOrder = orderRepository.findById(orderId);
		OrderDetailsDto orderDetailsDto = new OrderDetailsDto();

		if (optionalOrder.isPresent()) {
			orderDetailsDto.setOrderAmount(optionalOrder.get().getAmount());
			List<ProductDto> productDtoList = new ArrayList<>();

			for (CartItem cartItem : optionalOrder.get().getCartItems()) {
				ProductDto productDto = new ProductDto();
				productDto.setId(cartItem.getProduct().getId());
				productDto.setName(cartItem.getProduct().getName());
				productDto.setPrice(cartItem.getProduct().getPrice());
				productDto.setQuantity(cartItem.getQuantity());

				productDto.setByteImage(cartItem.getProduct().getImage());
				productDtoList.add(productDto);
			}
			orderDetailsDto.setProductDtos(productDtoList);
		}
		return orderDetailsDto;
	}

	public ReviewDto createReview(ReviewDto reviewDto) throws IOException {
		Optional<Product> optionalProduct = productRepository.findById(reviewDto.getProductId());
		Optional<User> optionalUser = userRepository.findById(reviewDto.getUserId());

		if (optionalProduct.isPresent() && optionalUser.isPresent()) {
			Review review = new Review();
			review.setRating(reviewDto.getRating());
			review.setDescription(reviewDto.getDescription());
			review.setUser(optionalUser.get());
			review.setProduct(optionalProduct.get());
			review.setImage(reviewDto.getImage().getBytes());
			return reviewRepository.save(review).getReviewDto();
		}
		return null;

	}
}
