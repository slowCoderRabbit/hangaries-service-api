package com.hangaries.repository;

import com.hangaries.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    //  List<Customer> findByLoginId(String loginId);

    @Query(value = "select * from CUSTOMER_MASTER where mobile_number=:mobNo", nativeQuery = true)
    Customer getCustomerById(@RequestParam("mobNo") String mobNo) throws Exception;

    //
    // List<Customer> deleteByLoginId(String loginId);
    @Query(value = "select count(*) from CUSTOMER_MASTER where mobile_number=:mobnumber", nativeQuery = true)
    int getCustomerRegisterStatus(@Param("mobnumber") String mobnumber) throws Exception;

    @Query(value = "select id from CUSTOMER_MASTER where mobile_number=:mobnumber", nativeQuery = true)
    long getCustomerIdByMobNo(@Param("mobnumber") String mobnumber) throws Exception;

    @Query(value = "select * from CUSTOMER_MASTER where mobile_number=:mobnumber", nativeQuery = true)
    Customer getCustomerDtlsByMobNum(@Param("mobnumber") String mobnumber) throws Exception;

}
