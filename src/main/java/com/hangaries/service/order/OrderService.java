package com.hangaries.service.order;


import com.hangaries.model.*;
import com.hangaries.model.vo.OrderVO;

import java.util.List;

public interface OrderService {

    String getNewOrderId(OrderIdInput orderIdInput);

    List<Order> getOrderById(String OrderId);

    List<Order> updateOrderStatus(String orderId, String status, String updatedBy);

    Order saveOrder(Order order);

    List<OrderDetail> saveOrderDetails(List<OrderDetail> orderDetails);

    List<OrderDetail> getOrderDetailsByOrderId(String orderId);

    List<Order> getOrderByCustomerId(int customerId);

    List<OrderProcessingDetails> getOrderProcessingDetailsByOrderId(String orderId);

    List<OrderWithCustomerDetail> getOrderAndCustomerDetailsByCustomerId(int parseInt);

    List<OrderVO> getOrderMenuIngredientAddressView(String restaurantId, String storeId, String mobileNumber);

    List<OrderVO> saveOrderAndGetOrderView(Order orderRequest);

    List<OrderVO> queryOrderViewByParams(OrderQueryRequest orderRequest);

    List<OrderDetail> updateOrderDetailsStatus(String orderId, String orderStatus, String updatedBy);

    List<OrderVO> updateOrderDetailsStatusBySubProductId(String orderId, String productId, String subProductId, String status, String updatedBy);

    List<Order> updateDeliveryUserByOrderId(String orderId, String deliveryUser, String updateOn);

    List<Order> updateFoodPackagedFlagByOrderId(String orderId, String foodPackagedFlag, String updateOn);
}
