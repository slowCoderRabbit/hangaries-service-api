package com.hangaries.repository;

import com.hangaries.model.Order;
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
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByOrderId(String orderId);

    List<Order> findByCustomerId(int customerId);

    @Modifying
    @Query(value = "update ORDER_MASTER om set om.order_status = :status,om.updated_by =:updatedBy, updated_date =:updatedOn where om.order_id = :orderId", nativeQuery = true)
    int updateOrderStatus(@Param("orderId") String orderId, @Param("status") String status, @Param("updatedBy") String updatedBy, @Param("updatedOn") Date updatedOn);

    @Modifying
    @Query(value = "update ORDER_MASTER om set om.order_status = :status, om.payment_status = :paymentStatus, om.updated_by =:updatedBy,updated_date =:updatedOn where om.order_id = :orderId", nativeQuery = true)
    int updateOrderAndPaymentStatus(@Param("orderId") String orderId, @Param("status") String status, @Param("paymentStatus") String paymentStatus, @Param("updatedBy") String updatedBy, @Param("updatedOn") Date updatedOn);

    @Modifying
    @Query(value = "update ORDER_MASTER om set om.delivery_user_id = :deliveryUser, om.updated_by =:updatedBy,updated_date =:updatedOn where om.order_id = :orderId", nativeQuery = true)
    int updateDeliveryUserByOrderId(@Param("orderId") String orderId, @Param("deliveryUser") String deliveryUser, @Param("updatedBy") String updatedBy, @Param("updatedOn") Date updatedOn);

    @Modifying
    @Query(value = "update ORDER_MASTER om set om.food_packaged_flag = :foodPackagedFlag, om.updated_by =:updatedBy,updated_date =:updatedOn where om.order_id = :orderId", nativeQuery = true)
    int updateFoodPackagedFlagByOrderId(@Param("orderId") String orderId, @Param("foodPackagedFlag") String foodPackagedFlag, @Param("updatedBy") String updatedBy, @Param("updatedOn") Date updatedOn);

    @Modifying
    @Query(value = "update ORDER_MASTER om set om.payment_mode=:paymentMode, om.payment_status = :paymentStatus,om.order_status = :status, om.updated_by =:updatedBy,updated_date =:updatedOn where om.order_id = :orderId", nativeQuery = true)
    int updatePaymentModeByOrderId(@Param("orderId") String orderId, @Param("paymentMode") String paymentMode, @Param("paymentStatus") String paymentStatus, @Param("status") String status, @Param("updatedBy") String updatedBy, @Param("updatedOn") Date updatedOn);
}
