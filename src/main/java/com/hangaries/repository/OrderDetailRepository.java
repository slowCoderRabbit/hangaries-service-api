package com.hangaries.repository;

import com.hangaries.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findByOrderId(String orderId);

    @Modifying
    @Query(value = "update ORDER_DETAILS set order_detail_status = :status,updated_by =:updatedBy, updated_date =:updatedOn where order_id = :orderId", nativeQuery = true)
    int updateOrderDetailsStatus(@Param("orderId") String orderId, @Param("status") String status, @Param("updatedBy") String updatedBy, @Param("updatedOn") Date updatedOn);

    @Modifying
    @Query(value = "update ORDER_DETAILS set order_detail_status = :status,updated_by =:updatedBy, updated_date =:updatedOn where order_id = :orderId and product_id = :productId and sub_product_Id = :subProductId ", nativeQuery = true)
    int updateOrderDetailsStatusBySubProductId(@Param("orderId") String orderId, @Param("productId") String productId,
                                               @Param("subProductId") String subProductId, @Param("status") String status, @Param("updatedBy") String updatedBy, @Param("updatedOn") Date updatedOn);

    @Query(value = "select * from ORDER_DETAILS where order_id = :orderId and product_id = :productId and sub_product_Id = :subProductId", nativeQuery = true)
    OrderDetail getOrderDetailsStatusBySubProductId(@Param("orderId") String orderId, @Param("productId") String productId, @Param("subProductId") String subProductId);

    @Modifying
    @Query(value = "update ORDER_DETAILS set food_packaged_flag = :foodPackagedFlag,updated_by =:updatedBy, updated_date =:updatedOn where product_id = :productId \n" +
            "and sub_product_Id = :subProductId and  order_id = :orderId", nativeQuery = true)
    int updateFoodPackagedFlagForOrderItem(@Param("orderId") String orderId, @Param("productId") String productId,
                                           @Param("subProductId") String subProductId, @Param("foodPackagedFlag") String foodPackagedFlag, @Param("updatedBy") String updatedBy, @Param("updatedOn") Date updatedOn);

    @Query(value = "select (select count(1) from ORDER_DETAILS  where order_id = :orderId) \n" +
            "- (select count(1) from ORDER_DETAILS  where order_id = :orderId \n" +
            "and order_detail_status = 'FOOD READY' and food_packaged_flag='Y')\n" +
            "as total_count", nativeQuery = true)
    int isFoodReadyAndPacked(@Param("orderId") String orderId);
}
