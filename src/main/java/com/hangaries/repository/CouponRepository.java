package com.hangaries.repository;

import com.hangaries.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    @Query(value = "select * from (select effective_start_date, effective_end_date from COUPON_MASTER where coupon_code =:couponCode) AS CM, \n" +
            " COUPON_MASTER M where coupon_code =:couponCode and curdate() BETWEEN DATE(CM.effective_start_date) and DATE(CM.effective_end_date) and active_flag =:activeFlag", nativeQuery = true)
    Coupon validateCoupon(@Param("couponCode") String couponCode, @Param("activeFlag") String activeFlag);
}
