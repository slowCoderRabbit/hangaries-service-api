package com.hangaries.service.order.impl;

import com.hangaries.model.*;
import com.hangaries.model.dto.OrderMenuIngredientAddressDTO;
import com.hangaries.model.vo.OrderDetailsVO;
import com.hangaries.model.vo.OrderVO;
import com.hangaries.model.wera.request.WERAOrderAcceptRequest;
import com.hangaries.model.wera.request.WERAOrderFoodReadyRequest;
import com.hangaries.model.wera.request.WeraOrderRequestDetail;
import com.hangaries.repository.*;
import com.hangaries.service.config.impl.ConfigServiceImpl;
import com.hangaries.service.order.OrderService;
import com.hangaries.service.store.impl.StoreServiceImpl;
import com.hangaries.service.wera.WERACallbackServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

import static com.hangaries.constants.HangariesConstants.*;
import static com.hangaries.util.HangariesUtil.generatorQueryString;

@Service
public class OrderServiceImpl implements OrderService {

    public static final String ORDER_SOURCE = "ORDER_SOURCE";
    public static final String SYSTEM = "SYSTEM";
    public static final String ORDER_BY_CREATED_DATE = " order by created_date";
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
    @Autowired
    ConfigServiceImpl configService;
    @Autowired
    WERACallbackServiceImpl weraCallbackService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

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
    public List<Order> updateOrderStatus(String orderId, String status, String updatedBy) {

        String paymentStatus = autoUpdatePaymentStatus(status);
        if (!StringUtils.isBlank(paymentStatus)) {
            orderRepository.updateOrderAndPaymentStatus(orderId, status, paymentStatus, updatedBy, new Date());
        } else {
            orderRepository.updateOrderStatus(orderId, status, updatedBy, new Date());
        }

        performWERAAction(orderId, status, updatedBy);

        return updateOrderProcessing(orderId);
    }

    private void performWERAAction(String orderId, String status, String updatedBy) {
        try {
            logger.info("Checking order status for WERA processing [{}]", status);
            if (PROCESSING.equals(status)) {
                acceptWERAOrder(orderId);
            } else if (FOOD_READY.equals(status)) {
                foodReadyWERAOrder(orderId);
            }

        } catch (Exception ex) {
            logger.error("Exception in performWERAAction!!", ex);
        }
    }

    private void foodReadyWERAOrder(String orderId) {

        WeraOrderRequestDetail weraOrderRequestDetail = getWeraOrderRequestDetails(orderId);
        if (weraOrderRequestDetail.isWeraOrder() && StringUtils.isNotBlank(weraOrderRequestDetail.getMerchant_id())) {
            WERAOrderFoodReadyRequest request = new WERAOrderFoodReadyRequest();
            request.setOrder_id(weraOrderRequestDetail.getOrders().stream().findFirst().get().getAggregatorOrderId());
            request.setMerchant_id(weraOrderRequestDetail.getMerchant_id());
            logger.info("WERA food ready request = [{}].", request);
            weraCallbackService.callWERAOrderFoodReadyAPI(request);
        }

    }

    private void acceptWERAOrder(String orderId) {

        WeraOrderRequestDetail weraOrderRequestDetail = getWeraOrderRequestDetails(orderId);

        if (weraOrderRequestDetail.isWeraOrder() && StringUtils.isNotBlank(weraOrderRequestDetail.getMerchant_id())) {
            WERAOrderAcceptRequest request = new WERAOrderAcceptRequest();
            request.setOrder_id(weraOrderRequestDetail.getOrders().stream().findFirst().get().getAggregatorOrderId());
            request.setMerchant_id(weraOrderRequestDetail.getMerchant_id());
            request.setPreparation_time(30);
            logger.info("WERA Accept request = [{}].", request);
            weraCallbackService.callWERAOrderAcceptAPI(request);
        }
    }

    WeraOrderRequestDetail getWeraOrderRequestDetails(String orderId) {
        boolean isWERAOrder = false;
        String storeId = "";
        String weraMerchantId = "";
        WeraOrderRequestDetail weraOrderRequest = new WeraOrderRequestDetail();
        List<Order> order = orderRepository.findByOrderId(orderId);
        if (null != order && !order.isEmpty()) {
            isWERAOrder = order.stream().findFirst().get().getOrderChannel().equalsIgnoreCase(WERA);
            storeId = order.stream().findFirst().get().getStoreId();
        }
        logger.info("isWERAOrder = [{}] for order ID [{}].", isWERAOrder, orderId);
        logger.info("storeId = [{}] for order ID [{}].", storeId, orderId);
        if (isWERAOrder) {
            Map<String, Store> storeDetailMap = StoreServiceImpl.getWeraMerchantToStoreMapping();

            for (Map.Entry<String, Store> entry : storeDetailMap.entrySet()) {
                if (entry.getValue().getStoreId().equals(storeId)) {
                    weraMerchantId = entry.getKey();
                }
            }
        }
        weraOrderRequest.setWeraOrder(isWERAOrder);
        weraOrderRequest.setOrder_id(orderId);
        weraOrderRequest.setMerchant_id(weraMerchantId);
        weraOrderRequest.setOrders(order);
        return weraOrderRequest;
    }

    public List<Order> updatePaymentModeByOrderId(String orderId, String paymentMode, String paymentStatus, String orderStatus, String updatedBy) {

        orderRepository.updatePaymentModeByOrderId(orderId, paymentMode, paymentStatus, orderStatus, updatedBy, new Date());
        return updateOrderProcessing(orderId);
    }

    private List<Order> updateOrderProcessing(String orderId) {
        List<Order> savedOrders = orderRepository.findByOrderId(orderId);
        logger.info("Order status updated for orderID = {}. Updating OrderProcessingDetails....!!!", orderId);
        for (Order order : savedOrders) {
            OrderProcessingDetails detailsOP = getNewOrderProcessingDetails(order);
            saveOrderProcessingDetails(detailsOP);
        }

        return savedOrders;
    }

    private String autoUpdatePaymentStatus(String status) {

        if (status.equalsIgnoreCase(DELIVERED)) {
            return PAID;
        }
        return "";
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
        order.setOrderChannel(getOrderChannelFromOrder(order));
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            orderDetail.setOrderId(newOrderId);
            orderDetail.setOrderDetailStatus(order.getOrderStatus());

        }
        BusinessDate businessDate = configService.getBusinessDate(order.getRestaurantId(), order.getStoreId());
        if (null != businessDate) {
            order.setOrderReceivedDateTime(businessDate.getBusinessDate());
            logger.info("Setting order setOrderReceivedDateTime = [{}].", order.getOrderReceivedDateTime());
        }

        Order savedOrder = orderRepository.save(order);
        logger.info("Order saved for new orderID = {}. Updating OrderProcessingDetails....!!!", newOrderId);
        OrderProcessingDetails detailsOP = getNewOrderProcessingDetails(order);
        saveOrderProcessingDetails(detailsOP);
        if (isAutoAcceptOrdrSource(order)) {
            orderRepository.updateOrderStatus(newOrderId, ACCEPTED, order.getUpdatedBy(), new Date());
            orderDetailRepository.updateOrderDetailsStatus(newOrderId, ACCEPTED, order.getUpdatedBy(), new Date());
            detailsOP.setOrderStatus(ACCEPTED);
            Instant later = Instant.now().plusSeconds(1);
            Date date = Date.from(later);
            detailsOP.setCreatedDate(date);
            detailsOP.setUpdatedDate(date);
            saveOrderProcessingDetails(detailsOP);


        }
        return savedOrder;
    }

    private String getOrderChannelFromOrder(Order order) {
        String orderChannel = ADMIN;
        if (StringUtils.isNotBlank(order.getOrderChannel())) {
            return order.getOrderChannel();
        } else if (StringUtils.isNotBlank(order.getOrderSource()) && order.getOrderSource().equalsIgnoreCase(WD)) {
            orderChannel = ONLINE;
        }
        return orderChannel;
    }

    private boolean isAutoAcceptOrdrSource(Order order) {
        List<ConfigMaster> configDetails = getConfigMasterList(order);
        for (ConfigMaster config : configDetails) {
            if (config.getConfigCriteriaValue().equalsIgnoreCase(order.getOrderSource())) {
                return config.getConfigValue().equalsIgnoreCase("Y");
            }
        }
        return false;
    }

    @Override
    public List<OrderVO> saveOrderAndGetOrderView(Order orderRequest) {
        Order savedOrder = saveOrder(orderRequest);
        OrderQueryRequest orderQueryRequest = new OrderQueryRequest();
        orderQueryRequest.setOrderId(savedOrder.getOrderId());
        List<OrderVO> orderList = queryOrderViewByParams(orderQueryRequest);
        return orderList;

    }

    @Override
    public List<OrderVO> queryOrderViewByParams(OrderQueryRequest orderRequest) {

        String queryString = generatorQueryString(orderRequest);
        if (StringUtils.isBlank(queryString)) {
            queryString = "SELECT * FROM vOrderMenuIngredientAddress";
        } else {
            queryString = "SELECT * FROM vOrderMenuIngredientAddress where " + queryString;
        }
        queryString = queryString + ORDER_BY_CREATED_DATE;
        if (!orderRequest.isDescending()) {
            queryString = queryString + " desc";
        }
        queryString = queryString + ", order_id, id desc, product_id, sub_product_id";
        logger.info("Querying view using queryString = [{}]", queryString);
        List<OrderMenuIngredientAddressDTO> results = jdbcTemplate.query(queryString, BeanPropertyRowMapper.newInstance(OrderMenuIngredientAddressDTO.class));
        logger.info("queryOrderViewByParams :: Total records returned from DB = [{}].", results.size());
        Map<String, List<OrderMenuIngredientAddressDTO>> orderMap = consolidateResponseToOrderedMapByOrderId(results);
        logger.info("queryOrderViewByParams :: Result consolidated in to = [{}].", orderMap.size());
        List<OrderVO> orderList = convertOrderDTOMapTOOrderVOList(orderMap);
        logger.info("queryOrderViewByParams :: Final order list created of size = [{}].", orderList.size());
        //Collections.sort(orderList, Comparator.comparing(OrderVO::getCreatedDate));
        return orderList;
    }

    @Override
    public List<OrderDetail> updateOrderDetailsStatus(String orderId, String orderStatus, String updatedBy) {
        orderDetailRepository.updateOrderDetailsStatus(orderId, orderStatus, updatedBy, new Date());
        checkForOrderStatusUpdate(orderId, updatedBy);
        return orderDetailRepository.findByOrderId(orderId);

    }

    @Override
    public List<OrderVO> updateOrderDetailsStatusBySubProductId(String orderId, String productId, String subProductId, String status, String updatedBy) {
        orderDetailRepository.updateOrderDetailsStatusBySubProductId(orderId, productId, subProductId, status, updatedBy, new Date());
        if (FOOD_READY.equals(status)) {
            checkForOrderStatusUpdate(orderId, updatedBy);
        }
        OrderQueryRequest orderQueryRequest = new OrderQueryRequest();
        orderQueryRequest.setOrderId(orderId);
        List<OrderVO> orderList = queryOrderViewByParams(orderQueryRequest);
        return orderList;
    }

    private void checkForOrderStatusUpdate(String orderId, String updatedBy) {
        if (isFoodReadyAndPacked(orderId)) {
            orderRepository.updateFoodPackagedFlagByOrderId(orderId, Y, updatedBy, new Date());
            updateOrderStatus(orderId, FOOD_READY, updatedBy);
        }
    }

    private boolean isFoodReadyAndPacked(String orderId) {
        int result = orderDetailRepository.isFoodReadyAndPacked(orderId);
        logger.info("isFoodReadyAndPacked = [{}] for orderID = [{}].", result, orderId);
        return 0 == result;
    }

    @Override
    public List<Order> updateDeliveryUserByOrderId(String orderId, String deliveryUser, String updatedBy) {
        orderRepository.updateDeliveryUserByOrderId(orderId, deliveryUser, updatedBy, new Date());
        return orderRepository.findByOrderId(orderId);
    }

    @Override
    public List<Order> updateFoodPackagedFlagByOrderId(String orderId, String foodPackagedFlag, String updatedBy) {
        orderRepository.updateFoodPackagedFlagByOrderId(orderId, foodPackagedFlag, updatedBy, new Date());
        return orderRepository.findByOrderId(orderId);
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
        detailsOP.setCreatedBy(user.getLoginId());
        detailsOP.setUpdatedBy(user.getLoginId());

        return detailsOP;
    }


//    User getRoleCategoryByOrderSource(Order order) {
//
//        Predicate<ConfigMaster> isOnlineOrderSource = s -> s.getConfigCriteriaValue().equals(order.getOrderSource());
//        List<ConfigMaster> configDetails = getConfigMasterList(order);
//
//        boolean isOnlineSource = configDetails.stream().anyMatch(isOnlineOrderSource);
//
//        if (isOnlineSource) {
//            return userRepository.findByLoginId(SYSTEM);
//        } else {
//            return userRepository.findByLoginId("STORE MANGER");
//        }
//
//    }

    User getRoleCategoryByOrderSource(Order order) {
        return userRepository.findByLoginId(order.getUpdatedBy());
    }

    private List<ConfigMaster> getConfigMasterList(Order order) {
        List<ConfigMaster> configDetails = configMasterRepository.getDetailsFromConfigMaster(order.getRestaurantId(), ALL, ORDER_SOURCE);
        return configDetails;
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

        for (Order order : orders) {
            orderWithCustomerDetail = new OrderWithCustomerDetail();
            orderWithCustomerDetail.setOrder(order);
            orderWithCustomerDetail.setCustomerDetails(getCustomerAddressDtlsById(order.getCustomerAddressId()));
            details.add(orderWithCustomerDetail);

        }

        return details;
    }

    @Override
    public List<OrderVO> getOrderMenuIngredientAddressView(String restaurantId, String storeId, String mobileNumber) {
        long startTime = System.nanoTime();
        logger.info("getOrderMenuIngredientAddressView :: Request received at [{}].", new Date());
        List<OrderMenuIngredientAddressDTO> results = orderMenuIngredientAddressRepository.getOrderMenuIngredientAddressView(restaurantId, storeId, mobileNumber);
        logger.info("getOrderMenuIngredientAddressView :: Total records returned from DB = [{}].", results.size());
        Map<String, List<OrderMenuIngredientAddressDTO>> orderMap = consolidateResponseToOrderedMapByOrderId(results);
        logger.info("getOrderMenuIngredientAddressView :: Result consolidated in to = [{}].", orderMap.size());
        List<OrderVO> orderList = convertOrderDTOMapTOOrderVOList(orderMap);
        logger.info("getOrderMenuIngredientAddressView :: Final order list created of size = [{}].", orderList.size());
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        logger.info("getOrderMenuIngredientAddressView :: Response generated at [{}].", new Date());
        logger.info("getOrderMenuIngredientAddressView :: Response processing took [{}] ms.", duration);
        return orderList;
    }


    private List<OrderVO> convertOrderDTOMapTOOrderVOList(Map<String, List<OrderMenuIngredientAddressDTO>> orderMap) {
        List<OrderDetailsVO> orderDetailsVOList;
        OrderVO orderVO;
        List<OrderVO> orderList = new ArrayList<>();
        for (List<OrderMenuIngredientAddressDTO> orderDTOList : orderMap.values()) {
            orderVO = new OrderVO();
            orderDetailsVOList = new ArrayList<>();
            for (int i = 0; i < orderDTOList.size(); i++) {
                if (i == 0) {
                    orderVO = populateOrderVO(orderDTOList.get(i));
                }
                orderDetailsVOList.add(populateOrderDetailsVO(orderDTOList.get(i)));

            }
            orderVO.setOrderDetails(orderDetailsVOList);
            orderList.add(orderVO);

        }
        return orderList;
    }

    Map<String, List<OrderMenuIngredientAddressDTO>> consolidateResponseToOrderedMapByOrderId(List<OrderMenuIngredientAddressDTO> results) {
        Map<String, List<OrderMenuIngredientAddressDTO>> orderMap = new LinkedHashMap<>();
        for (OrderMenuIngredientAddressDTO result : results) {
            List<OrderMenuIngredientAddressDTO> exitingList = orderMap.get(result.getOrderId());
            if (exitingList == null) {
                List<OrderMenuIngredientAddressDTO> newList = new ArrayList<>();
                newList.add(result);
                orderMap.put(result.getOrderId(), newList);
            } else {
                exitingList.add(result);
                orderMap.put(result.getOrderId(), exitingList);
            }
        }
        return orderMap;
    }

    private OrderDetailsVO populateOrderDetailsVO(OrderMenuIngredientAddressDTO result) {

        OrderDetailsVO orderDetailsVO = new OrderDetailsVO();

        orderDetailsVO.setOrderId(result.getOrderId());
        orderDetailsVO.setProductId(result.getProductId());
        orderDetailsVO.setProductName(result.getDishType());
        orderDetailsVO.setSubProductId(result.getSubProductId());
        orderDetailsVO.setIngredient(result.getIngredientType());
        orderDetailsVO.setQuantity(result.getQuantity());
        orderDetailsVO.setPrice(result.getPrice());
        orderDetailsVO.setRemarks(result.getRemarks());
        orderDetailsVO.setOrderDetailStatus(result.getOrderDetailStatus());
        orderDetailsVO.setKdsRoutingName(result.getKdsRoutingName());
        orderDetailsVO.setOrderDetailFoodPackagedFlag(result.getOrderDetailFoodPackagedFlag());
        return orderDetailsVO;
    }

    OrderVO populateOrderVO(OrderMenuIngredientAddressDTO result) {
        OrderVO vo = new OrderVO();
        vo.setOrderId(result.getOrderId());
        vo.setRestaurantId(result.getRestaurantId());
        vo.setRestaurantName(result.getRestaurantName());
        vo.setStoreId(result.getStoreId());
        vo.setStoreName(result.getStoreName());
        vo.setOrderSource(result.getOrderSource());
        vo.setCustomerId(result.getCustomerId());
        vo.setCustomerName(result.getCustomerName());
        vo.setOrderDeliveryType(result.getOrderDeliveryType());
        vo.setStoreTableId(result.getStoreTableId());
        vo.setStoreId(result.getStoreId());
        vo.setOrderStatus(result.getOrderStatus());
        vo.setPaymentStatus(result.getPaymentStatus());
        vo.setPaymentMode(result.getPaymentMode());
        vo.setPaymentTxnReference(result.getPaymentTxnReference());
        vo.setTaxRuleId(result.getTaxRuleId());
        vo.setTotalPrice(result.getTotalPrice());
        vo.setCgstCalculatedValue(result.getCgstCalculatedValue());
        vo.setSgstCalculatedValue(result.getSgstCalculatedValue());
        vo.setDeliveryCharges(result.getDeliveryCharges());
        vo.setOverallPriceWithTax(result.getOverallPriceWithTax());
        vo.setCreatedBy(result.getCreatedBy());
        vo.setCreatedDate(result.getCreatedDate());
        vo.setUpdatedBy(result.getUpdatedBy());
        vo.setUpdatedDate(result.getUpdatedDate());
        vo.setCustomerAddressId(result.getCustomerAddressId());
        vo.setMobileNumber(result.getMobileNumber());
        vo.setAddress(result.getAddress());
        vo.setDeliveryUserId(result.getDeliveryUserId());
        vo.setFoodPackagedFlag(result.getFoodPackagedFlag());
        vo.setCouponCode(result.getCouponCode());
        vo.setDiscountPercentage(result.getDiscountPercentage());
        vo.setOrderReceivedDateTime(result.getOrderReceivedDateTime());
        return vo;


    }

    private CustomerDtls getCustomerAddressDtlsById(long addressId) {
        return customerDtlsRepository.getCustomerAddressDtlsById(addressId);
    }


    public List<Order> updateFoodPackagedFlagForOrderItem(String orderId, String productId, String subProductId, String foodPackagedFlag, String updatedBy) {
        orderDetailRepository.updateFoodPackagedFlagForOrderItem(orderId, productId, subProductId, foodPackagedFlag, updatedBy, new Date());
        if (Y.equals(foodPackagedFlag)) {
            checkForOrderStatusUpdate(orderId, updatedBy);
        }
        return orderRepository.findByOrderId(orderId);
    }
}
