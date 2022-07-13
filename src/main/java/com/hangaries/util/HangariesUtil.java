package com.hangaries.util;

import com.hangaries.model.OrderQueryRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HangariesUtil {


    public static final String AND = " and ";
    private static final Logger logger = LoggerFactory.getLogger(HangariesUtil.class);

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

        if (!StringUtils.isBlank(orderRequest.getOrderDeliveryType())) {
            queryString.append("order_delivery_type = '" + orderRequest.getOrderDeliveryType() + "'");
            queryString.append(AND);
        }

        if (!StringUtils.isBlank(orderRequest.getDeliveryUserId())) {
            queryString.append("delivery_user_id = '" + orderRequest.getDeliveryUserId() + "'");
            queryString.append(AND);
        }

        if (!StringUtils.isBlank(orderRequest.getFoodPackagedFlag())) {
            queryString.append("food_packaged_flag = '" + orderRequest.getFoodPackagedFlag() + "'");
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
