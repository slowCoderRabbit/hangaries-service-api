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

    @Modifying
    @Query(value = "update ORDER_MASTER om set om.order_status = :status where om.order_id = :orderId", nativeQuery = true)
    int updateOrderStatus(@Param("orderId") String orderId, @Param("status") String status);
}
