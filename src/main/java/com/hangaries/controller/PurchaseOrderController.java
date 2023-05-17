package com.hangaries.controller;

import com.hangaries.model.PurchaseOrder;
import com.hangaries.model.PurchaseOrderWithName;
import com.hangaries.model.inventory.request.PurchaseOrderStatusRequest;
import com.hangaries.service.inventory.supplier.PurchaseOrderServiceImpl;
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
public class PurchaseOrderController {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderController.class);

    @Autowired
    private PurchaseOrderServiceImpl purchaseOrderService;

    @GetMapping("getAllPurchaseOrders")
    public ResponseEntity<List<PurchaseOrderWithName>> getAllPurchaseOrders(@RequestParam("restaurantId") String restaurantId) {
        List<PurchaseOrderWithName> purchaseOrders = new ArrayList<>();
        logger.info("Getting list of all purchase orders for restaurantId = [{}].");
        purchaseOrders = purchaseOrderService.getAllPurchaseOrders(restaurantId);
        logger.info("[{}] purchase orders found.", purchaseOrders.size());
        return new ResponseEntity<List<PurchaseOrderWithName>>(purchaseOrders, HttpStatus.OK);
    }

    @GetMapping("getPurchaseOrdersByStatus")
    public ResponseEntity<List<PurchaseOrderWithName>> getPurchaseOrdersByStatus(@RequestParam("restaurantId") String restaurantId, @RequestParam String status) {
        List<PurchaseOrderWithName> purchaseOrders = new ArrayList<>();
        logger.info("Getting list of all purchase orders for restaurantId = [{}], with status = [{}].", restaurantId, status);
        purchaseOrders = purchaseOrderService.getPurchaseOrdersByStatus(restaurantId, status);
        logger.info("[{}] [{}] purchase orders found.", purchaseOrders.size(), status);
        return new ResponseEntity<List<PurchaseOrderWithName>>(purchaseOrders, HttpStatus.OK);
    }

    @GetMapping("getPurchaseOrdersExcludingStatus")
    public ResponseEntity<List<PurchaseOrderWithName>> getPurchaseOrdersExcludingStatus(@RequestParam("restaurantId") String restaurantId, @RequestParam String status) {
        List<PurchaseOrderWithName> purchaseOrders = new ArrayList<>();
        logger.info("Getting list of all purchase orders for restaurantId = [{}] and status <> [{}].", restaurantId, status);
        purchaseOrders = purchaseOrderService.getPurchaseOrdersExcludingStatus(restaurantId, status);
        logger.info("[{}] <> [{}] purchase orders found.", purchaseOrders.size(), status);
        return new ResponseEntity<List<PurchaseOrderWithName>>(purchaseOrders, HttpStatus.OK);
    }

    @PostMapping("savePurchaseOrder")
    public PurchaseOrder savePurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        logger.info("Saving purchase order with details = [{}].", purchaseOrder);
        return purchaseOrderService.savePurchaseOrder(purchaseOrder);
    }

    @PostMapping("savePurchaseOrderStatus")
    public PurchaseOrder savePurchaseOrderStatus(@RequestBody PurchaseOrderStatusRequest request) {
        logger.info("Saving purchase order status = [{}] for id = [{}].", request.getItemStatus(), request.getPurchaseOrderId());
        return purchaseOrderService.savePurchaseOrderStatus(request);
    }
}
