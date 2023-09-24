package com.hangaries.service.coupon;

import com.hangaries.model.Coupon;
import com.hangaries.model.CouponResponse;

import java.util.List;

public interface CouponService {
    Coupon saveCoupon(Coupon coupon);

    CouponResponse validateCoupon(String couponCode, String restaurantId, String storeId);

    List<Coupon> getCouponsByRestroAndStore(String restaurantId, String storeId);
}
