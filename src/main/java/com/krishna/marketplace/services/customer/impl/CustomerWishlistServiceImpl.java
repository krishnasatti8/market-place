package com.krishna.marketplace.services.customer.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krishna.marketplace.dto.WishlistDto;
import com.krishna.marketplace.model.Product;
import com.krishna.marketplace.model.User;
import com.krishna.marketplace.model.Wishlist;
import com.krishna.marketplace.repository.ProductRepository;
import com.krishna.marketplace.repository.UserRepository;
import com.krishna.marketplace.repository.WishlistRepository;
import com.krishna.marketplace.services.customer.CustomerWishlistService;

@Service
public class CustomerWishlistServiceImpl implements CustomerWishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public WishlistDto addProductToWishlist(WishlistDto wishlistDto) {
        Optional<Product> optionalProduct = productRepository.findById(wishlistDto.getProductId());
        Optional<User> optionalUser = userRepository.findById(wishlistDto.getUserId());

        if (optionalProduct.isPresent() && optionalUser.isPresent()) {

            List<Wishlist> wishlist = wishlistRepository.findByProductIdAndUserId(wishlistDto.getProductId(),
                    wishlistDto.getUserId());
            if (wishlist.isEmpty()) {
                Wishlist newWishlist = new Wishlist();
                newWishlist.setProduct(optionalProduct.get());
                newWishlist.setUser(optionalUser.get());
                return wishlistRepository.save(newWishlist).getWishlistDto();
            } else {
                return new WishlistDto();
            }
        }
        return null;
    }

    @Override
    public List<WishlistDto> getWishlistByUserId(Long userId) {
        return wishlistRepository.findAllByUserId(userId).stream().map(Wishlist::getWishlistDto)
                .collect(java.util.stream.Collectors.toList());
    }
}
