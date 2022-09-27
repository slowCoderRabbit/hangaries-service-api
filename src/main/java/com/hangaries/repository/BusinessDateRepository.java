package com.hangaries.repository;

import com.hangaries.model.BusinessDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessDateRepository extends JpaRepository<BusinessDate, Long> {
    @Query(value = "select * from BUSINESS_DATE_MASTER where restaurant_id=:restaurantId and store_id=:storeId", nativeQuery = true)
    BusinessDate getBusinessDate(@Param("restaurantId") String restaurantId, @Param("storeId") String storeId);

}
