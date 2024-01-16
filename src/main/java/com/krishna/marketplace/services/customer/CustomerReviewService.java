package com.krishna.marketplace.services.customer;

import java.io.IOException;

import com.krishna.marketplace.dto.OrderDetailsDto;
import com.krishna.marketplace.dto.ReviewDto;

public interface CustomerReviewService {
    public OrderDetailsDto getOrderDetails(Long orderId);
    public ReviewDto createReview(ReviewDto reviewDto) throws IOException;
}
