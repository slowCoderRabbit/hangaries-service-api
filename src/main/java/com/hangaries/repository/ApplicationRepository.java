package com.hangaries.repository;

import com.hangaries.model.AppDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<AppDetails, String> {

    List<AppDetails> findByRestaurantIdAndStoreId(String restaurantId, String storeId);
}
