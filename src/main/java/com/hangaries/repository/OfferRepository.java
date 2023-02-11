package com.hangaries.repository;

import com.hangaries.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query(value = "select * from OFFERS_MASTER where offer_status=:status", nativeQuery = true)
    List<Offer> getOffersByStatus(@Param("status") String status);

    List<Offer> findByRestaurantIdAndStoreId(String restaurantId, String storeId);

    List<Offer> findByRestaurantIdAndStoreIdAndOfferStatus(String restaurantId, String storeId, String status);
}
