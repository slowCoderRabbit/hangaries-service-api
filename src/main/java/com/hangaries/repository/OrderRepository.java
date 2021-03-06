package com.hangaries.repository;

import com.hangaries.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByOrderId(String orderId);

    List<Order> findByCustomerId(int customerId);

    @Modifying
    @Query(value = "update ORDER_MASTER om set om.order_status = :status where om.order_id = :orderId", nativeQuery = true)
    int updateOrderStatus(@Param("orderId") String orderId, @Param("status") String status);

    @Modifying
    @Query(value = "update ORDER_MASTER om set om.order_status = :status, om.payment_status = :paymentStatus where om.order_id = :orderId", nativeQuery = true)
    int updateOrderAndPaymentStatus(@Param("orderId") String orderId, @Param("status") String status, @Param("paymentStatus") String paymentStatus);

    @Modifying
    @Query(value = "update ORDER_MASTER om set om.delivery_user_id = :deliveryUser where om.order_id = :orderId", nativeQuery = true)
    int updateDeliveryUserByOrderId(@Param("orderId") String orderId, @Param("deliveryUser") String deliveryUser);

    @Modifying
    @Query(value = "update ORDER_MASTER om set om.food_packaged_flag = :foodPackagedFlag where om.order_id = :orderId", nativeQuery = true)
    int updateFoodPackagedFlagByOrderId(@Param("orderId") String orderId, @Param("foodPackagedFlag") String foodPackagedFlag);
}
