package com.krishna.marketplace.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.krishna.marketplace.dto.WishlistDto;
import com.krishna.marketplace.services.customer.CustomerWishlistService;

@RestController
@RequestMapping("/api/customer")
public class CustomerWishlistController {

    @Autowired
    private CustomerWishlistService customerWishlistService;

    @PostMapping("/addtowishlist")
    public ResponseEntity<?> addProductToWishlist(@RequestBody WishlistDto wishlistDto) {
        WishlistDto wishlistDtoResponse = customerWishlistService.addProductToWishlist(wishlistDto);
        if (wishlistDtoResponse != null) {
            return ResponseEntity.ok(wishlistDtoResponse);
        }
        return ResponseEntity.badRequest().body("Something went wrong!");
    }

    @PostMapping("/removefromwishlist")
    public ResponseEntity<?> removeProductFromWishlist(@RequestBody WishlistDto wishlistDto) {
        WishlistDto wishlistDtoResponse = customerWishlistService.removeProductFromWhistlist(wishlistDto);
        if (wishlistDtoResponse != null) {
            return ResponseEntity.ok(wishlistDtoResponse);
        }
        return ResponseEntity.badRequest().body("Something went wrong!");
    }

    @GetMapping("/fetchwishlist/{userId}")
    public ResponseEntity<?> getWishlistByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(customerWishlistService.getWishlistByUserId(userId));
    }

}
