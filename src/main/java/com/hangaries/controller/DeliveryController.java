package com.hangaries.controller;

import com.hangaries.model.DeliveryConfig;
import com.hangaries.model.TaxMaster;
import com.hangaries.service.delivery.impl.DeliveryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class DeliveryController {

    private static final Logger logger = LoggerFactory.getLogger(DeliveryController.class);

    @Autowired
    private DeliveryServiceImpl deliveryService;

    @GetMapping("getDeliveryConfigByCriteria")
    public ResponseEntity<List<DeliveryConfig>> getDeliveryConfigByCriteria(@RequestParam("restaurantId") String restaurantId, @RequestParam("storeId") String storeId, @RequestParam("criteria") String criteria) {
        List<DeliveryConfig> deliveryConfig = new ArrayList<>();
        try {
            logger.info("Get delivery config by restaurantId = {},  storeId = {} and criteria = {}.", restaurantId,storeId,criteria);
            deliveryConfig = deliveryService.getDeliveryConfigByCriteria(restaurantId,storeId,criteria);
            return new ResponseEntity<List<DeliveryConfig>>(deliveryConfig, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting tax rule details::" + ex.getMessage());
            return new ResponseEntity<List<DeliveryConfig>>(deliveryConfig, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
