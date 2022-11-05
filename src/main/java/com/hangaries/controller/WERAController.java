package com.hangaries.controller;

import com.hangaries.model.wera.request.WERAOrderAcceptRequest;
import com.hangaries.model.wera.request.WERAOrderFoodReadyRequest;
import com.hangaries.model.wera.request.WeraOrder;
import com.hangaries.model.wera.request.WeraUploadMenu;
import com.hangaries.model.wera.response.WERAOrderAcceptResponse;
import com.hangaries.model.wera.response.WERAOrderFoodReadyResponse;
import com.hangaries.model.wera.response.WeraOrderResponse;
import com.hangaries.service.wera.WERACallbackServiceImpl;
import com.hangaries.service.wera.WERAMenuServiceImpl;
import com.hangaries.service.wera.WERAOrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class WERAController {

    private static final Logger logger = LoggerFactory.getLogger(WERAController.class);

    @Autowired
    WERAMenuServiceImpl weraMenuService;

    @Autowired
    WERAOrderServiceImpl weraOrderService;

    @Autowired
    WERACallbackServiceImpl weraCallbackService;

    @GetMapping("uploadMenuToWeraFoods")
    WeraUploadMenu uploadMenuToWeraFoods(@RequestParam("restaurantId") String restaurantId,
                                         @RequestParam("storeId") String storeId) {
        logger.info("Getting menu to upload for restaurantId = {}, storeId = {}.", restaurantId, storeId);
        return weraMenuService.uploadMenuToWeraFoods(restaurantId, storeId);

    }

    @PostMapping("placeWeraOrder")
    WeraOrderResponse placeOrder(@RequestBody WeraOrder order) {
        logger.info("Wera order received  = [{}].", order);
        return weraOrderService.placeOrder(order);
    }

    @PostMapping("acceptWeraOrder")
    ResponseEntity<WERAOrderAcceptResponse> acceptOrder(@RequestBody WERAOrderAcceptRequest request) {
        logger.info("Accept WERA order details  = [{}].", request);
        return weraCallbackService.callWERAOrderAcceptAPI(request);
    }

    @PostMapping("foodReadyWeraOrder")
    ResponseEntity<WERAOrderFoodReadyResponse> foodReadyWeraOrder(@RequestBody WERAOrderFoodReadyRequest request) {
        logger.info("Food Ready WERA order details  = [{}].", request);
        return weraCallbackService.callWERAOrderFoodReadyAPI(request);
    }


}
