package com.hangaries.repository;

import com.hangaries.model.CustomerDtls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CustomerDtlsRepository extends JpaRepository<CustomerDtls, Long> {
    @Query(value = "select count(*) from CUSTOMER_ADDRESS_DETAILS where mobile_number=:mobnumber and customer_address_type=:type", nativeQuery = true)
    public int getCustomerStatus(@Param("mobnumber") String mobnumber, @Param("type") String type) throws Exception;

    @Query(value = "select * from CUSTOMER_ADDRESS_DETAILS where mobile_number=:mobnumber and customer_address_type=:type", nativeQuery = true)
    public CustomerDtls getCustometDtlsById(@Param("mobnumber") String mobnumber, @Param("type") String type) throws Exception;

    @Query(value = "select * from hangaries.CUSTOMER_ADDRESS_DETAILS where mobile_number=:mobnumber and active=:status", nativeQuery = true)
    public List<CustomerDtls> getCustomerAddressDtlsByMobNum(@Param("mobnumber") String mobnumber,@Param("status")String status) throws Exception;

    @Modifying
    @Transactional
    @Query(value = "update   CUSTOMER_ADDRESS_DETAILS set active='N' where mobile_number=:mobnumber and customer_address_type=:type", nativeQuery = true)
    public void deleteAddressByType(@Param("mobnumber") String mobnumber, @Param("type") String type) throws Exception;
}
