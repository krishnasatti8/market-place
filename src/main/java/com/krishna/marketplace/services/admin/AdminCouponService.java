package com.krishna.marketplace.services.admin;

import java.util.List;

import com.krishna.marketplace.model.Coupon;

public interface AdminCouponService {
    public Coupon createCoupon(Coupon coupon);
    public List<Coupon> getAllCoupons();
}
