package com.hangaries.repository;

import com.hangaries.model.OrderProcessingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProcessingDetailsRepository extends JpaRepository<OrderProcessingDetails, Long> {
    List<OrderProcessingDetails> getOrderProcessingDetailsByOrderId(String orderId);
}
