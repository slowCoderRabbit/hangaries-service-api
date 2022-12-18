package com.hangaries.service.delivery.impl;

import com.hangaries.model.DeliveryConfig;
import com.hangaries.repository.DeliveryRepository;
import com.hangaries.service.delivery.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Override
    public List<DeliveryConfig> getDeliveryConfigByCriteria(String restaurantId, String storeId, String criteria) {
        return deliveryRepository.getDeliveryConfigByCriteria(restaurantId, storeId, criteria);
    }

    public DeliveryConfig saveDeliveryConfigByCriteria(DeliveryConfig config) {
        return deliveryRepository.save(config);
    }
}
