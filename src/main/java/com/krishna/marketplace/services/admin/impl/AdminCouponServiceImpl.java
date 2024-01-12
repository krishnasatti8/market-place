package com.krishna.marketplace.services.admin.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.krishna.marketplace.exceptions.ValidationException;
import com.krishna.marketplace.model.Coupon;
import com.krishna.marketplace.repository.CouponRepository;
import com.krishna.marketplace.services.admin.AdminCouponService;

@Service
public class AdminCouponServiceImpl implements AdminCouponService {

	@Autowired
	private CouponRepository couponRepository;

	@Override
	public Coupon createCoupon(Coupon coupon) {
		if (couponRepository.existsByCode(coupon.getCode())) {
			throw new ValidationException("Coupon code already exists");

		} else {
			return couponRepository.save(coupon);
		}
	}


    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

}
