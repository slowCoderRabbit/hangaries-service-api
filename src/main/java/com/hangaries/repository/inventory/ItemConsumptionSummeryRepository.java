package com.hangaries.repository.inventory;

import com.hangaries.model.ItemConsumptionSummery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemConsumptionSummeryRepository extends JpaRepository<ItemConsumptionSummery, Long> {
    @Procedure(procedureName = "sp_InventoryUpdateEOD", outputParameterName = "oErrorDescription")
    String inventoryUpdateEOD(@Param("iRestaurant_id") String iRestaurant_id, @Param("iStore_id") String iStore_id);

}
