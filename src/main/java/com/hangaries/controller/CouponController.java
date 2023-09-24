package com.hangaries.controller;

import com.hangaries.model.Coupon;
import com.hangaries.model.CouponResponse;
import com.hangaries.service.coupon.impl.CouponServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("validateCoupon")
    @ResponseBody
    public ResponseEntity<CouponResponse> validateCoupon(@RequestParam String couponCode, @RequestParam("restaurantId") String restaurantId, @RequestParam("storeId") String storeId) {
        logger.info("Validating coupon code = {} for restaurantId = {} and storeId = {}. ", couponCode, restaurantId, storeId);
        CouponResponse couponResponse = couponService.validateCoupon(couponCode, restaurantId, storeId);
        return new ResponseEntity<CouponResponse>(couponResponse, HttpStatus.OK);

    }

    @GetMapping("getCouponsByRestroAndStore")
    @ResponseBody
    public ResponseEntity<List<Coupon>> getCouponsByRestroAndStore(@RequestParam("restaurantId") String restaurantId, @RequestParam("storeId") String storeId) {
        logger.info("Getting coupons for restaurantId = {} and storeId = {}. ", restaurantId, storeId);
        List<Coupon> couponList = couponService.getCouponsByRestroAndStore(restaurantId, storeId);
        logger.info("{} coupons found for restaurantId = {} and storeId = {}.", couponList.size(), restaurantId, storeId);
        return new ResponseEntity<List<Coupon>>(couponList, HttpStatus.OK);

    }

}
