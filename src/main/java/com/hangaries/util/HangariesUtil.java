package com.hangaries.util;

import com.hangaries.model.OrderQueryRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class HangariesUtil {

    public static final Map<String, String> fieldToColumnMap;
    public static final String AND = " and ";
    private static final Logger logger = LoggerFactory.getLogger(HangariesUtil.class);

    static {
        fieldToColumnMap = new HashMap<>();
        fieldToColumnMap.put("mobileNumber", "mobile_number");
        fieldToColumnMap.put("orderId", "order_id");
        fieldToColumnMap.put("restaurantId", "restaurant_id");
        fieldToColumnMap.put("storeId", "store_Id");
        fieldToColumnMap.put("orderReceivedDateTime", "order_received_date_time");
        fieldToColumnMap.put("orderStatus", "order_status");
        fieldToColumnMap.put("paymentStatus", "payment_status");
    }

    public static String generatorQueryString(OrderQueryRequest orderRequest) {

        StringBuilder queryString = new StringBuilder();
        String finalQuery = null;

        if (!StringUtils.isBlank(orderRequest.getOrderId())) {
            queryString.append("order_id = '" + orderRequest.getOrderId() + "'");
            queryString.append(AND);
        }
        if (!StringUtils.isBlank(orderRequest.getRestaurantId())) {
            queryString.append("restaurant_id = '" + orderRequest.getRestaurantId() + "'");
            queryString.append(AND);
        }
        if (!StringUtils.isBlank(orderRequest.getStoreId())) {
            queryString.append("store_Id = '" + orderRequest.getStoreId() + "'");
            queryString.append(AND);
        }
        if (!StringUtils.isBlank(orderRequest.getMobileNumber())) {
            queryString.append("mobile_number = '" + orderRequest.getMobileNumber() + "'");
            queryString.append(AND);
        }
        if (!StringUtils.isBlank(orderRequest.getOrderStatus())) {
            queryString.append("order_status = '" + orderRequest.getOrderStatus() + "'");
            queryString.append(AND);
        }
        if (!StringUtils.isBlank(orderRequest.getPaymentStatus())) {
            queryString.append("payment_status = '" + orderRequest.getPaymentStatus() + "'");
            queryString.append(AND);
        }

        if (!StringUtils.isBlank(orderRequest.getOrderReceivedDate())) {
            queryString.append("DATE(order_received_date_time) = DATE('" + orderRequest.getOrderReceivedDate() + "')");
            queryString.append(AND);
        }

        if (!StringUtils.isBlank(queryString)) {
            finalQuery = queryString.substring(0, queryString.lastIndexOf(AND));
        }

        logger.info("finalQuery = {}", finalQuery);

        return finalQuery;
    }

    public static void main(String[] args) {
        logger.info("Calling Main method.....!!");
    }

}
