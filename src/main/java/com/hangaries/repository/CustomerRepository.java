package com.hangaries.repository;

import com.hangaries.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //  List<Customer> findByLoginId(String loginId);

    @Query(value = "select * from CUSTOMER_MASTER where mobile_number=:mobNo and restaurant_id=:restaurantId", nativeQuery = true)
    Customer getCustomerById(@Param("restaurantId") String restaurantId, @RequestParam("mobNo") String mobNo) throws Exception;

    //
    // List<Customer> deleteByLoginId(String loginId);
    @Query(value = "select count(*) from CUSTOMER_MASTER where mobile_number=:mobnumber and restaurant_id=:restaurantId", nativeQuery = true)
    int getCustomerRegisterStatus(@Param("restaurantId") String restaurantId, @Param("mobnumber") String mobnumber) throws Exception;

    @Query(value = "select id from CUSTOMER_MASTER where mobile_number=:mobnumber and restaurant_id=:restaurantId", nativeQuery = true)
    long getCustomerIdByMobNo(@Param("restaurantId") String restaurantId, @Param("mobnumber") String mobnumber) throws Exception;

    @Query(value = "select * from CUSTOMER_MASTER where mobile_number=:mobnumber and restaurant_id=:restaurantId", nativeQuery = true)
    Customer getCustomerDtlsByMobNum(@Param("restaurantId") String restaurantId, @Param("mobnumber") String mobnumber) throws Exception;

    List<Customer> findByMobileNumber(String phone_number);

}
