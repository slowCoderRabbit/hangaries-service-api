package com.hangaries.service.coupon.impl;

import com.hangaries.model.Coupon;
import com.hangaries.model.CouponResponse;
import com.hangaries.repository.CouponRepository;
import com.hangaries.service.coupon.CouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.hangaries.constants.HangariesConstants.Y;

@Service
public class CouponServiceImpl implements CouponService {

    public static final String VALID_COUPON = "VALID_COUPON";
    private static final Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);
    private static final String INVALID_COUPON = "INVALID_COUPON";

    @Autowired
    CouponRepository couponRepository;

    @Override
    public Coupon saveCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public CouponResponse validateCoupon(String couponCode) {
        Coupon coupon = couponRepository.validateCoupon(couponCode, Y);
        CouponResponse response = new CouponResponse();
        if (null != coupon) {
            response.setValidationResult(VALID_COUPON);
            response.setCouponDetails(coupon);
        } else {
            response.setValidationResult(INVALID_COUPON);

        }
        logger.info("Coupon validation result = [{}]", response);
        return response;
    }
}
