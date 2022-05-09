package com.hangaries.repository;

import com.hangaries.model.RSSDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RSSDateRepository extends JpaRepository<RSSDate, Long> {

    @Query(value = "select * from REPORT_SALES_SUMMARY_BY_DATE where restaurant_id=:restaurantId and store_id=:storeId", nativeQuery = true)
    List<RSSDate> getReport(@Param("restaurantId") String restaurantId, @Param("storeId") String storeId);

}
