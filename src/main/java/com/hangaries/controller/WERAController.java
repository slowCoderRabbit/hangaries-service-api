package com.hangaries.controller;

import com.hangaries.model.wera.request.WeraOrder;
import com.hangaries.model.wera.request.WeraUploadMenu;
import com.hangaries.model.wera.response.WeraOrderResponse;
import com.hangaries.service.wera.WERAMenuServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class WERAController {

    private static final Logger logger = LoggerFactory.getLogger(WERAController.class);

    @Autowired
    WERAMenuServiceImpl weraMenuService;

    @GetMapping("uploadMenuToWeraFoods")
    List<WeraUploadMenu> uploadMenuToWeraFoods(@RequestParam("restaurantId") String restaurantId,
                                               @RequestParam("storeId") String storeId) {
        logger.info("Getting menu to upload for restaurantId = {}, storeId = {}.", restaurantId, storeId);
        return weraMenuService.uploadMenuToWeraFoods(restaurantId, storeId);

    }

    @PostMapping("placeOrder")
    WeraOrderResponse placeOrder(@RequestBody WeraOrder order) {
        logger.info("Wera order received  = [{}].", order);
        return weraMenuService.placeOrder(order);
    }


}
