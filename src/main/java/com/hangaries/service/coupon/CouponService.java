package com.hangaries.service.coupon;

import com.hangaries.model.Coupon;
import com.hangaries.model.CouponResponse;

import java.util.List;

public interface CouponService {
    Coupon saveCoupon(Coupon coupon);

    CouponResponse validateCoupon(String coupon);

    List<Coupon> getCouponsByRestroAndStore(String restaurantId, String storeId);
}
