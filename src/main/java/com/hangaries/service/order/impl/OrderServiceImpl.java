package com.hangaries.service.order.impl;

import com.hangaries.model.*;
import com.hangaries.model.dto.OrderMenuIngredientAddressDTO;

import com.hangaries.repository.*;
import com.hangaries.service.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

@Service
public class OrderServiceImpl implements OrderService {

    public static final String ORDER_SOURCE = "ORDER_SOURCE";
    public static final String SYSTEM = "SYSTEM";
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    OrderIdRepository orderIdRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    OrderProcessingDetailsRepository orderProcessingDetailsRepository;

    @Autowired
    ConfigMasterRepository configMasterRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CustomerDtlsRepository customerDtlsRepository;

    @Autowired
    OrderMenuIngredientAddressRepository orderMenuIngredientAddressRepository;



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
        List<Order> savedOrders = orderRepository.findByOrderId(orderId);
        logger.info("Order status updated for orderID = {}. Updating OrderProcessingDetails....!!!",orderId);
        for (Order order : savedOrders) {
            OrderProcessingDetails detailsOP = getNewOrderProcessingDetails(order);
            saveOrderProcessingDetails(detailsOP);
        }

        return savedOrders;
    }

    @Override
    public Order saveOrder(Order order) {

        logger.info("Generating new orderID.....");
        OrderIdInput input = new OrderIdInput();
        input.setRestaurantId(order.getRestaurantId());
        input.setStoreId(order.getStoreId());
        input.setOrderSource(order.getOrderSource());
        String newOrderId = getNewOrderId(input);
        order.setOrderId(newOrderId);
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            orderDetail.setOrderId(newOrderId);
        }
        Order savedOrder = orderRepository.save(order);
        logger.info("Order saved for new orderID = {}. Updating OrderProcessingDetails....!!!", newOrderId);
        OrderProcessingDetails detailsOP = getNewOrderProcessingDetails(order);
        saveOrderProcessingDetails(detailsOP);
        return savedOrder;
    }

    OrderProcessingDetails saveOrderProcessingDetails(OrderProcessingDetails detailsOP) {
        logger.info("Saving order process details for orderID = {} and order status = {}.", detailsOP.getOrderId(), detailsOP.getOrderStatus());
        OrderProcessingDetails details = orderProcessingDetailsRepository.save(detailsOP);
        logger.info("Order process details saved for orderID = {} and order status = {}.", detailsOP.getOrderId(), detailsOP.getOrderStatus());
        return details;
    }

    OrderProcessingDetails getNewOrderProcessingDetails(Order order) {
        logger.info("Creating OrderProcessingDetails for orderID = {}", order.getOrderId());
        OrderProcessingDetails detailsOP = new OrderProcessingDetails();

        detailsOP.setOrderId(order.getOrderId());
        detailsOP.setOrderStatus(order.getOrderStatus());
        detailsOP.setRestaurantId(order.getRestaurantId());
        detailsOP.setStoreId(order.getStoreId());
        User user = getRoleCategoryByOrderSource(order);
        detailsOP.setRoleCategory(user.getRoleCategory());
        detailsOP.setUserSeqNo(user.getUserSeqNo());

        return detailsOP;
    }


    User getRoleCategoryByOrderSource(Order order) {

        Predicate<ConfigMaster> isOnlineOrderSource = s -> s.getConfigCriteriaValue().equals(order.getOrderSource());
        List<ConfigMaster> configDetails = configMasterRepository.getDetailsFromConfigMaster(order.getRestaurantId(), order.getStoreId(), ORDER_SOURCE);

        boolean isOnlineSource = configDetails.stream().anyMatch(isOnlineOrderSource);

        if (isOnlineSource) {
            return userRepository.findByLoginId(SYSTEM);
        } else {
            return userRepository.findByLoginId("STORE MANGER");
        }

    }


    @Override
    public List<OrderDetail> saveOrderDetails(List<OrderDetail> orderDetails) {
        return orderDetailRepository.saveAll(orderDetails);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(String orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }

    @Override
    public List<Order> getOrderByCustomerId(int customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @Override
    public List<OrderProcessingDetails> getOrderProcessingDetailsByOrderId(String orderId) {
        return orderProcessingDetailsRepository.getOrderProcessingDetailsByOrderId(orderId);
    }

    @Override
    public List<OrderWithCustomerDetail> getOrderAndCustomerDetailsByCustomerId(int customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);

        List<OrderWithCustomerDetail> details = new ArrayList<>();
        OrderWithCustomerDetail orderWithCustomerDetail = null;

        for(Order order :orders){
            orderWithCustomerDetail = new OrderWithCustomerDetail();


            orderWithCustomerDetail.setOrder(order);
            orderWithCustomerDetail.setCustomerDetails(getCustomerAddressDtlsById(order.getCustomerAddressId()));
            details.add(orderWithCustomerDetail);

        }

        return details;
    }

    @Override
    public List<OrderMenuIngredientAddressDTO> getOrderMenuIngredientAddressView(String restaurantId, String storeId, String mobileNumber) {

        List<OrderMenuIngredientAddressDTO>  result =  orderMenuIngredientAddressRepository.getOrderMenuIngredientAddressView(restaurantId,storeId,mobileNumber);
        return result;
    }

    private CustomerDtls getCustomerAddressDtlsById(long addressId) {
       return customerDtlsRepository.getCustomerAddressDtlsById(addressId);
    }


}
