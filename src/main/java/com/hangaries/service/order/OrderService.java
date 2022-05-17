package com.hangaries.service.order;


import com.hangaries.model.*;
import com.hangaries.model.dto.OrderMenuIngredientAddressDTO;

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

    List<OrderWithCustomerDetail> getOrderAndCustomerDetailsByCustomerId(int parseInt);

    List<OrderMenuIngredientAddressDTO> getOrderMenuIngredientAddressView(String restaurantId, String storeId, String mobileNumber);
}
