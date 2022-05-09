package com.hangaries.repository;

import com.hangaries.model.RSSOrderSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RSSOrderSourceRepository extends JpaRepository<RSSOrderSource, Long> {

    @Query(value = "select * from REPORT_SALES_SUMMARY_BY_ORDER_SOURCE where restaurant_id=:restaurantId and store_id=:storeId", nativeQuery = true)
    List<RSSOrderSource> getReport(@Param("restaurantId") String restaurantId, @Param("storeId") String storeId);

}
