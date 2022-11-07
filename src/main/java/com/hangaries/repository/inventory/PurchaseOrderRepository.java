package com.hangaries.repository.inventory;

import com.hangaries.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

    @Query(value = "select * from PURCHASE_ORDER where purchase_order_status=:status order by id", nativeQuery = true)
    List<PurchaseOrder> getPurchaseOrdersByStatus(@Param("status") String status);

    @Modifying
    @Transactional
    @Query(value = "update PURCHASE_ORDER set purchase_order_status=:status, updated_by=:updatedBy,updated_date=:updatedDate where id=:id", nativeQuery = true)
    int savePurchaseOrderStatus(@Param("id") long id, @Param("status") String status, @Param("updatedBy") String updatedBy, @Param("updatedDate") Date updatedDate);

    @Query(value = "select * from PURCHASE_ORDER where id=:id", nativeQuery = true)
    PurchaseOrder getPurchaseOrderById(@Param("id") long id);


    @Query(value = "select * from PURCHASE_ORDER where purchase_order_status<>:status order by id", nativeQuery = true)
    List<PurchaseOrder> getPurchaseOrdersExcludingStatus(@Param("status") String status);
}
