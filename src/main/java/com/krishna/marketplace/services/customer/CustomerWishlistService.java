package com.krishna.marketplace.services.customer;

import java.util.List;

import com.krishna.marketplace.dto.WishlistDto;

public interface CustomerWishlistService {
    public WishlistDto addProductToWishlist(WishlistDto wishlistDto);
    public WishlistDto removeProductFromWhistlist(WishlistDto wishlistDto);
    public List<WishlistDto> getWishlistByUserId(Long userId);
}
