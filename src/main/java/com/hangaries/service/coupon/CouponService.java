package com.hangaries.service.coupon;

import com.hangaries.model.Coupon;
import com.hangaries.model.CouponResponse;

public interface CouponService {
    Coupon saveCoupon(Coupon coupon);

    CouponResponse validateCoupon(String coupon);
}
