package com.krishna.marketplace.controller.customer;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krishna.marketplace.dto.OrderDetailsDto;
import com.krishna.marketplace.dto.ReviewDto;
import com.krishna.marketplace.services.customer.CustomerReviewService;

@RestController
@RequestMapping("/api/customer")
public class CustomerReviewController {

    @Autowired
    private CustomerReviewService customerReviewService;

    @GetMapping("orderdetails/{orderId}")
    public ResponseEntity<OrderDetailsDto> getOrderDetails(@PathVariable Long orderId) {
        OrderDetailsDto orderDetailsDto = customerReviewService.getOrderDetails(orderId);
        return ResponseEntity.ok(orderDetailsDto);
    }

    @PostMapping("createreview")
    public ResponseEntity<?> createReview(@ModelAttribute ReviewDto reviewDto) throws IOException {
        ReviewDto reviewDtoResponse = customerReviewService.createReview(reviewDto);
        if (reviewDtoResponse == null) {
            return ResponseEntity.badRequest().body("Something went wrong");
        }
        return ResponseEntity.ok(reviewDtoResponse);
    }

}
