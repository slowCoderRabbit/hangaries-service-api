package com.hangaries.controller;

import com.hangaries.model.*;
import com.hangaries.model.vo.OrderVO;
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

    @GetMapping("getOrderByCustomerId")
    public ResponseEntity<List<Order>> getOrderByCustomerId(@RequestParam("customerId") String customerId) {
        logger.info("Get Order by Customer Id = {}", customerId);

        List<Order> orderList = new ArrayList<>();
        try {
            orderList = orderService.getOrderByCustomerId(Integer.parseInt(customerId));
            return new ResponseEntity<List<Order>>(orderList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error getting order by Id = {} :: {}", customerId, ex.getMessage());
            return new ResponseEntity<List<Order>>(orderList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getOrderAndCustomerDetailsByCustomerId")
    public ResponseEntity<List<OrderWithCustomerDetail>> getOrderAndCustomerDetailsByCustomerId(@RequestParam("customerId") String customerId) {
        logger.info("Get Order by Customer Id = {}", customerId);

        List<OrderWithCustomerDetail> orderList = new ArrayList<>();
        try {
            orderList = orderService.getOrderAndCustomerDetailsByCustomerId(Integer.parseInt(customerId));
            return new ResponseEntity<List<OrderWithCustomerDetail>>(orderList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error getting order by Id = {} :: {}", customerId, ex.getMessage());
            return new ResponseEntity<List<OrderWithCustomerDetail>>(orderList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getOrderMenuIngredientAddressView")
    public ResponseEntity<List<OrderVO>> getOrderMenuIngredientAddressView(@RequestParam("restaurantId") String restaurantId,
                                                                           @RequestParam("storeId") String storeId,
                                                                           @RequestParam("mobileNumber") String mobileNumber) {
        logger.info("Get OrderMenuIngredientAddress view details for restaurantId = {}, storeId = {}, mobileNumber = {}", restaurantId, storeId, mobileNumber);

        List<OrderVO> orders = new ArrayList<>();
        try {
            orders = orderService.getOrderMenuIngredientAddressView(restaurantId, storeId, mobileNumber);
            return new ResponseEntity<List<OrderVO>>(orders, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error getting order by mobileNumber = {} :: {}", mobileNumber, ex.getMessage());
            return new ResponseEntity<List<OrderVO>>(orders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getOrderProcessingDetailsByOrderId")
    public ResponseEntity<List<OrderProcessingDetails>> getOrderProcessingDetailsByOrderId(@RequestParam("orderId") String orderId) {
        logger.info("Get Order Processing Details by Id = {}", orderId);

        List<OrderProcessingDetails> orderProcessingDetailsList = new ArrayList<>();
        try {
            orderProcessingDetailsList = orderService.getOrderProcessingDetailsByOrderId(orderId);
            return new ResponseEntity<List<OrderProcessingDetails>>(orderProcessingDetailsList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error getting order by Id = {} :: {}", orderId, ex.getMessage());
            return new ResponseEntity<List<OrderProcessingDetails>>(orderProcessingDetailsList, HttpStatus.INTERNAL_SERVER_ERROR);
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
    public ResponseEntity<List<OrderVO>> saveNewOrder(@Valid @RequestBody Order orderRequest) {
        logger.info("New Order received ", orderRequest);

        List<OrderVO> newOrder = new ArrayList<>();
        try {
            newOrder = orderService.saveOrderAndGetOrderView(orderRequest);
            return new ResponseEntity<List<OrderVO>>(newOrder, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error saving order by Id = {} :: {}", orderRequest, ex.getMessage());
            return new ResponseEntity<List<OrderVO>>(newOrder, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping("saveNewOrderDeprecated")
    public ResponseEntity<Order> saveNewOrderDeprecated(@Valid @RequestBody Order orderRequest) {
        logger.info("New Order received ", orderRequest);

        Order newOrder = new Order();
        try {
            newOrder = orderService.saveOrder(orderRequest);
            return new ResponseEntity<Order>(newOrder, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error saving order by Id = {} :: {}", orderRequest, ex.getMessage());
            return new ResponseEntity<Order>(newOrder, HttpStatus.INTERNAL_SERVER_ERROR);
        }

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

    @PostMapping("queryOrderViewByParams")
    public ResponseEntity<List<OrderVO>> queryOrderViewByParams(@RequestBody OrderQueryRequest orderRequest) {
        logger.info("queryOrderViewByParams :: Get Order Details by = [{}]", orderRequest);

        List<OrderVO> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = orderService.queryOrderViewByParams(orderRequest);
            return new ResponseEntity<List<OrderVO>>(orderDetailList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error getting order by params = {} :: {}", orderRequest, ex.getMessage());
            return new ResponseEntity<List<OrderVO>>(orderDetailList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("updateOrderDetailsStatusByOrderId")
    public ResponseEntity<List<OrderDetail>> updateOrderDetailsStatusByOrderId(@RequestParam("orderId") String orderId, @RequestParam("status") String status) {
        logger.info("Updating order Id = {} with order status = {}. ", orderId, status);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = orderService.updateOrderDetailsStatus(orderId, status);
            return new ResponseEntity<List<OrderDetail>>(orderDetailList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error getting order by Id = {} :: {}", orderId, ex.getMessage());
            return new ResponseEntity<List<OrderDetail>>(orderDetailList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("updateOrderDetailsStatusBySubProductId")
    public ResponseEntity<OrderDetail> updateOrderDetailsStatusBySubProductId(@RequestParam("orderId") String orderId, @RequestParam("status") String status,
                                                                              @RequestParam("productId") String productId, @RequestParam("subProductId") String subProductId) {
        logger.info("Updating orderId = [{}], productId  = [{}], subProductId  = [{}],   with order status = {}. ", orderId, productId, subProductId, status);

        OrderDetail orderDetail = null;
        try {
            orderDetail = orderService.updateOrderDetailsStatusBySubProductId(orderId, productId, subProductId, status);
            return new ResponseEntity<OrderDetail>(orderDetail, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error getting order by Id = {} :: {}", orderId, ex.getMessage());
            return new ResponseEntity<OrderDetail>(orderDetail, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("updateDeliveryUserByOrderId")
    public ResponseEntity<List<Order>> updateDeliveryUserByOrderId(@RequestParam("orderId") String orderId, @RequestParam("deliveryUser") String deliveryUser) {
        logger.info("Updating order Id = {} with deliveryUser = {}. ", orderId, deliveryUser);

        List<Order> orderList = new ArrayList<>();
        try {
            orderList = orderService.updateDeliveryUserByOrderId(orderId, deliveryUser);
            return new ResponseEntity<List<Order>>(orderList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error getting order by Id = {} :: {}", orderId, ex.getMessage());
            return new ResponseEntity<List<Order>>(orderList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("queryOrderViewByRequestParam")
//    public ResponseEntity<List<OrderVO>> queryOrderViewByRequestParam(@RequestParam("orderStatus") String orderStatus) {
//        logger.info("queryOrderViewByRequestParam 1 :: Get Order Details by = [{}]", orderStatus);
//
//        List<OrderVO> orderDetailList = new ArrayList<>();
//        OrderQueryRequest orderRequest = new OrderQueryRequest();
//        orderRequest.setOrderStatus(orderStatus);
//        logger.info("queryOrderViewByRequestParam 2 :: Get Order Details by = [{}]", orderRequest);
//        try {
//            orderDetailList = orderService.queryOrderViewByParams(orderRequest);
//            return new ResponseEntity<List<OrderVO>>(orderDetailList, HttpStatus.OK);
//        } catch (Exception ex) {
//            logger.error("Error getting order by params = {} :: {}", orderRequest, ex.getMessage());
//            return new ResponseEntity<List<OrderVO>>(orderDetailList, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @PostMapping("queryOrderViewByParamsPost")
//    public ResponseEntity<List<OrderVO>> queryOrderViewByParamsPost(@RequestBody OrderQueryRequest orderRequest) {
//        logger.info("queryOrderViewByParamsPost :: Get Order Details by = [{}]", orderRequest);
//
//        List<OrderVO> orderDetailList = new ArrayList<>();
//        try {
//            orderDetailList = orderService.queryOrderViewByParams(orderRequest);
//            return new ResponseEntity<List<OrderVO>>(orderDetailList, HttpStatus.OK);
//        } catch (Exception ex) {
//            logger.error("Error getting order by params = {} :: {}", orderRequest, ex.getMessage());
//            return new ResponseEntity<List<OrderVO>>(orderDetailList, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


}
