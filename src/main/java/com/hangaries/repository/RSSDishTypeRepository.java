package com.hangaries.repository;

import com.hangaries.model.RSSDishType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RSSDishTypeRepository extends JpaRepository<RSSDishType, Long> {

    @Query(value = "select * from REPORT_SALES_SUMMARY_BY_DISH_TYPE where restaurant_id=:restaurantId and store_id=:storeId", nativeQuery = true)
    List<RSSDishType> getReport(@Param("restaurantId") String restaurantId, @Param("storeId") String storeId);

}
