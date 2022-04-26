package com.hangaries.service.order;


import com.hangaries.model.Order;
import com.hangaries.model.OrderDetail;
import com.hangaries.model.OrderIdInput;
import com.hangaries.model.OrderProcessingDetails;

import java.util.List;

public interface OrderService {

    String getNewOrderId(OrderIdInput orderIdInput);

    List<Order> getOrderById(String OrderId);

    List<Order> updateOrderStatus(String orderId, String status);

    Order saveOrder(Order order);

    List<OrderDetail> saveOrderDetails(List<OrderDetail> orderDetails);

    List<OrderDetail> getOrderDetailsByOrderId(String orderId);

    List<Order> getOrderByCustomerId(int customerId);

    List<OrderProcessingDetails> getOrderProcessingDetailsByOrderId(String orderId);
}
