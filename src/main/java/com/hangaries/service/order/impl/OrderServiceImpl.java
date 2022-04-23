package com.hangaries.service.order.impl;

import com.hangaries.model.Order;
import com.hangaries.model.OrderDetail;
import com.hangaries.model.OrderId;
import com.hangaries.model.OrderIdInput;
import com.hangaries.repository.OrderDetailRepository;
import com.hangaries.repository.OrderIdRepository;
import com.hangaries.repository.OrderRepository;
import com.hangaries.service.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    OrderIdRepository orderIdRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;


    @Override
    public String getNewOrderId(OrderIdInput orderIdInput) {

        StringBuilder stringBuilder = new StringBuilder(orderIdInput.getOrderSource());
        stringBuilder.append(orderIdInput.getRestaurantId());
        stringBuilder.append(orderIdInput.getStoreId());
        String pattern = "yyyyMMdd";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        stringBuilder.append(format.format(new Date()));

        OrderId orderId = new OrderId();
        orderId.setPrefix(stringBuilder.toString());
        logger.info("Requesting new Deal ID with following details = {}", orderId);

        int currentOrderId = orderIdRepository.getLatestOrderId(orderIdInput.getRestaurantId(), orderIdInput.getStoreId());
        int newOrderId = currentOrderId + 1;
        orderIdRepository.saveNewOrderId(orderIdInput.getRestaurantId(), orderIdInput.getStoreId(), newOrderId);
        logger.info("Latest order id from database = {} and updated order Id = {}.", currentOrderId, newOrderId);


        String formattedId = String.format("%06d", newOrderId);
        stringBuilder.append(formattedId);

        logger.info("The newly created formatted order id = {}", stringBuilder);
        return stringBuilder.toString();

    }

//    @Override
//    public String getNewOrderId(OrderIdInput orderIdInput) {
//
//        StringBuilder stringBuilder = new StringBuilder(orderIdInput.getOrderSource());
//        stringBuilder.append(orderIdInput.getRestaurantId());
//        stringBuilder.append(orderIdInput.getStoreId());
//        String pattern = "yyyyMMdd";
//        SimpleDateFormat format = new SimpleDateFormat(pattern);
//        stringBuilder.append(format.format(new Date()));
//
//        OrderId orderId = new OrderId();
//        orderId.setPrefix(stringBuilder.toString());
//        logger.info("Requesting new Deal ID with following details = {}", orderId);
//
//        OrderId newOrderId = orderIdRepository.save(orderId);
//
//        StringBuilder newOrderIdString = new StringBuilder();
//
//        if (!isStrNullOrEmpty(newOrderId.getPrefix())) {
//            newOrderIdString.append(newOrderId.getPrefix());
//        }
//        String formattedId = String.format("%06d", newOrderId.getId());
//        newOrderIdString.append(formattedId);
//        if (!isStrNullOrEmpty(newOrderId.getSuffix())) {
//            newOrderIdString.append(newOrderId.getSuffix());
//        }
//
//        logger.info("The newly created formatted order id = {}", newOrderIdString);
//        return newOrderIdString.toString();
//
//    }

    private boolean isStrNullOrEmpty(String string) {
        return string == null || string.isEmpty() || string.trim().isEmpty();
    }

    @Override
    public List<Order> getOrderById(String orderId) {
        return orderRepository.findByOrderId(orderId);
    }

    @Override
    public List<Order> updateOrderStatus(String orderId, String status) {
        orderRepository.updateOrderStatus(orderId, status);
        return orderRepository.findByOrderId(orderId);
    }

    @Override
    public Order saveOrder(Order order) {
        OrderIdInput input = new OrderIdInput();
        input.setRestaurantId(order.getRestaurantId());
        input.setStoreId(order.getStoreId());
        input.setOrderSource(order.getOrderSource());
        String newOrderId = getNewOrderId(input);
        order.setOrderId(newOrderId);
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            orderDetail.setOrderId(newOrderId);
        }
        return orderRepository.save(order);
    }


    @Override
    public List<OrderDetail> saveOrderDetails(List<OrderDetail> orderDetails) {
        return orderDetailRepository.saveAll(orderDetails);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(String orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }


}
