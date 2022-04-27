package com.hangaries.service.delivery;

import com.hangaries.model.DeliveryConfig;

import java.util.List;

public interface DeliveryService {
    List<DeliveryConfig> getDeliveryConfigByCriteria(String restaurantId, String storeId, String criteria);
}
