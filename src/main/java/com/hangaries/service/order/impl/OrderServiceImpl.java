package com.hangaries.service.order.impl;

import com.hangaries.model.OrderId;
import com.hangaries.model.OrderIdInput;
import com.hangaries.repository.OrderIdRepository;
import com.hangaries.service.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    OrderIdRepository orderIdRepository;

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

        OrderId newOrderId = orderIdRepository.save(orderId);

        StringBuilder newOrderIdString = new StringBuilder();

        if (!isStrNullOrEmpty(newOrderId.getPrefix())) {
            newOrderIdString.append(newOrderId.getPrefix());
        }
        String formattedId = String.format("%06d", newOrderId.getId());
        newOrderIdString.append(formattedId);
        if (!isStrNullOrEmpty(newOrderId.getSuffix())) {
            newOrderIdString.append(newOrderId.getSuffix());
        }

        logger.info("The newly created formatted order id = {}", newOrderIdString);
        return newOrderIdString.toString();

    }

    private boolean isStrNullOrEmpty(String string) {
        return string == null || string.isEmpty() || string.trim().isEmpty();
    }

}
