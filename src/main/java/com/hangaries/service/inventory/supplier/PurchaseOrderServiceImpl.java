package com.hangaries.service.inventory.supplier;

import com.hangaries.model.PurchaseOrder;
import com.hangaries.model.inventory.request.PurchaseOrderStatusRequest;
import com.hangaries.repository.inventory.PurchaseOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.hangaries.constants.HangariesConstants.ACTIVE;

@Service
public class PurchaseOrderServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderServiceImpl.class);

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrder) {
        return purchaseOrderRepository.save(purchaseOrder);
    }

    public PurchaseOrder savePurchaseOrderStatus(PurchaseOrderStatusRequest request) {
        int result = purchaseOrderRepository.savePurchaseOrderStatus(request.getId(), request.getItemStatus(), request.getUpdatedBy(), new Date());
        logger.info("savePurchaseOrderStatus result = [{}]", result);
        return purchaseOrderRepository.getPurchaseOrderById(request.getId());
    }

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public List<PurchaseOrder> getAllActivePurchaseOrders() {
        return purchaseOrderRepository.getPurchaseOrdersByStatus(ACTIVE);
    }

    public List<PurchaseOrder> getPurchaseOrdersByStatus(String status) {
        return purchaseOrderRepository.getPurchaseOrdersByStatus(status);
    }

    public List<PurchaseOrder> getPurchaseOrdersExcludingStatus(String status) {
        return purchaseOrderRepository.getPurchaseOrdersExcludingStatus(status);
    }
}
