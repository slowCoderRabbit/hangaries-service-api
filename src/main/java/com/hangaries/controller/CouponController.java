package com.hangaries.controller;

import com.hangaries.model.Coupon;
import com.hangaries.service.coupon.impl.CouponServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class CouponController {

    private static final Logger logger = LoggerFactory.getLogger(CouponController.class);

    @Autowired
    CouponServiceImpl couponService;

    @PostMapping("saveCoupon")
    @ResponseBody
    public ResponseEntity<Coupon> saveCoupon(@RequestBody Coupon coupon) {
        logger.info("Saving coupon = {}. ", coupon);
        Coupon newCoupon = couponService.saveCoupon(coupon);
        return new ResponseEntity<Coupon>(newCoupon, HttpStatus.OK);

    }
}
