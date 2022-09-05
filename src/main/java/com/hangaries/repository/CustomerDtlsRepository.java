package com.hangaries.repository;

import com.hangaries.model.CustomerDtls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CustomerDtlsRepository extends JpaRepository<CustomerDtls, Long> {
    @Query(value = "select count(*) from CUSTOMER_ADDRESS_DETAILS where mobile_number=:mobnumber and customer_address_type=:type and active=:status", nativeQuery = true)
    int getCustomerStatus(@Param("mobnumber") String mobnumber, @Param("type") String type, @Param("status") String status) throws Exception;

    @Query(value = "select * from CUSTOMER_ADDRESS_DETAILS where mobile_number=:mobnumber and customer_address_type=:type and active=:status", nativeQuery = true)
    CustomerDtls getCustometDtlsById(@Param("mobnumber") String mobnumber, @Param("type") String type, @Param("status") String status) throws Exception;

    @Query(value = "select * from CUSTOMER_ADDRESS_DETAILS where mobile_number=:mobnumber and active=:status", nativeQuery = true)
    List<CustomerDtls> getCustomerAddressDtlsByMobNum(@Param("mobnumber") String mobnumber, @Param("status") String status) throws Exception;

    @Modifying
    @Transactional
    @Query(value = "update CUSTOMER_ADDRESS_DETAILS set active=:status where mobile_number=:mobnumber and customer_address_type=:type", nativeQuery = true)
    int updateAddressStatusByType(@Param("mobnumber") String mobnumber, @Param("type") String type, @Param("status") String status) throws Exception;

    CustomerDtls getCustomerAddressDtlsById(Long customerAddressId);
}
