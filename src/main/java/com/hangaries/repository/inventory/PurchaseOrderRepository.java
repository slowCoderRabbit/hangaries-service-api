package com.hangaries.repository.inventory;

import com.hangaries.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    @Query(value = "select * from PURCHASE_ORDER where purchase_order_status=:status order by purchase_order_id", nativeQuery = true)
    List<PurchaseOrder> getPurchaseOrdersByStatus(@Param("status") String status);

    @Modifying
    @Transactional
    @Query(value = "update PURCHASE_ORDER set purchase_order_status=:status, updated_by=:updatedBy,updated_date=:updatedDate where purchase_order_id=:purchaseOrderId", nativeQuery = true)
    int savePurchaseOrderStatus(@Param("purchaseOrderId") long purchaseOrderId, @Param("status") String status, @Param("updatedBy") String updatedBy, @Param("updatedDate") Date updatedDate);

    @Query(value = "select * from PURCHASE_ORDER where purchase_order_id=:purchaseOrderId", nativeQuery = true)
    PurchaseOrder getPurchaseOrderById(@Param("purchaseOrderId") long purchaseOrderId);


    @Query(value = "select * from PURCHASE_ORDER where purchase_order_status<>:status order by purchase_order_id", nativeQuery = true)
    List<PurchaseOrder> getPurchaseOrdersExcludingStatus(@Param("status") String status);

//    @Procedure(procedureName = "sp_updatePOtoConsumption", outputParameterName = "oErrorDescription")
//    String updatePOtoConsumption(@Param("purchaseOrderId") String purchaseOrderId);
}
