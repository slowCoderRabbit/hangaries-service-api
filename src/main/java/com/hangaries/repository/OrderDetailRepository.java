package com.hangaries.repository;

import com.hangaries.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findByOrderId(String orderId);

    @Modifying
    @Query(value = "update ORDER_DETAILS om set om.order_detail_status = :status where om.order_id = :orderId", nativeQuery = true)
    int updateOrderDetailsStatus(@Param("orderId") String orderId, @Param("status") String status);

}
