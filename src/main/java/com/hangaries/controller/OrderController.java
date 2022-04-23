package com.hangaries.controller;

import com.hangaries.model.Order;
import com.hangaries.model.OrderDetail;
import com.hangaries.model.OrderIdInput;
import com.hangaries.service.order.impl.OrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    @GetMapping("getOrderById")
    public ResponseEntity<List<Order>> getOrderById(@RequestParam("orderId") String orderId) {
        logger.info("Get Order by Id = {}", orderId);

        List<Order> orderList = new ArrayList<>();
        try {
            orderList = orderService.getOrderById(orderId);
            return new ResponseEntity<List<Order>>(orderList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error getting order by Id = {} :: {}", orderId, ex.getMessage());
            return new ResponseEntity<List<Order>>(orderList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("updateStatusByOrderId")
    public ResponseEntity<List<Order>> updateStatusByOrderId(@RequestParam("orderId") String orderId, @RequestParam("orderStatus") String orderStatus) {
        logger.info("Updating order Id = {} with order status = {}. ", orderId, orderStatus);

        List<Order> orderList = new ArrayList<>();
        try {
            orderList = orderService.updateOrderStatus(orderId, orderStatus);
            return new ResponseEntity<List<Order>>(orderList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error getting order by Id = {} :: {}", orderId, ex.getMessage());
            return new ResponseEntity<List<Order>>(orderList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("saveNewOrder")
    public ResponseEntity<Order> saveNewOrder(@Valid @RequestBody Order orderRequest) {
        logger.info("New Order received ", orderRequest);

        Order newOrder = new Order();

        newOrder = orderService.saveOrder(orderRequest);
        return new ResponseEntity<Order>(newOrder, HttpStatus.OK);

    }


    @PostMapping("saveNewOrderDetails")
    public ResponseEntity<List<OrderDetail>> saveNewOrderDetails(@Valid @RequestBody List<OrderDetail> orderDetails) {
        logger.info("New Order Details received with size = {} . ", orderDetails.size());

        List<OrderDetail> newOrderDetails = new ArrayList<>();
        try {
            newOrderDetails = orderService.saveOrderDetails(orderDetails);
            return new ResponseEntity<List<OrderDetail>>(newOrderDetails, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error getting order by Id = {} :: {}", newOrderDetails, ex.getMessage());
            return new ResponseEntity<List<OrderDetail>>(newOrderDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getOrderDetailsByOrderId")
    public ResponseEntity<List<OrderDetail>> getOrderDetailsByOrderId(@RequestParam("orderId") String orderId) {
        logger.info("Get Order Details by order Id = {}", orderId);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = orderService.getOrderDetailsByOrderId(orderId);
            return new ResponseEntity<List<OrderDetail>>(orderDetailList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error getting order by Id = {} :: {}", orderId, ex.getMessage());
            return new ResponseEntity<List<OrderDetail>>(orderDetailList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
