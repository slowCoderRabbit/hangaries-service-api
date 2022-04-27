package com.hangaries.repository;

import com.hangaries.model.DeliveryConfig;
import com.hangaries.model.TaxMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface DeliveryRepository extends JpaRepository<DeliveryConfig, Integer> {

    @Query(value = "select * from DELIVERY_CONFIG where restaurant_id=:restaurantId and store_id=:storeId and criteria=:criteria and default_criteria_flag='Y'", nativeQuery = true)
    List<DeliveryConfig> getDeliveryConfigByCriteria(String restaurantId, String storeId, String criteria);
}
