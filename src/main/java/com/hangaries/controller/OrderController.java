package com.hangaries.controller;

import com.hangaries.model.OrderIdInput;
import com.hangaries.service.order.impl.OrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderServiceImpl orderService;

    @PostMapping("getNewOrderId")
    HashMap<String, String> getNewOrderId(@Valid @RequestBody OrderIdInput orderIdInput) {
        logger.info("New Order Id details = " + orderIdInput.toString());
        String newOrderId = orderService.getNewOrderId(orderIdInput);
        HashMap<String, String> map = new HashMap<>();
        map.put("newOrderId", newOrderId);
        return map;
    }

}
