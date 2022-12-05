package com.hangaries.repository.inventory;

import com.hangaries.model.ItemConsumptionSummery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemConsumptionSummeryRepository extends JpaRepository<ItemConsumptionSummery, Long> {

}
