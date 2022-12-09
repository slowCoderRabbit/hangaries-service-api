package com.hangaries.service.inventory.supplier;

import com.hangaries.model.PurchaseOrder;
import com.hangaries.model.PurchaseOrderWithName;
import com.hangaries.model.inventory.request.PurchaseOrderStatusRequest;
import com.hangaries.repository.inventory.PurchaseOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.hangaries.constants.HangariesConstants.ACTIVE;
import static com.hangaries.constants.QueryStringConstants.*;

@Service
public class PurchaseOrderServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderServiceImpl.class);

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrder) {
        PurchaseOrder newPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        callSPUpdatePOtoConsumption(newPurchaseOrder);
        return newPurchaseOrder;
    }

    private void callSPUpdatePOtoConsumption(PurchaseOrder newPurchaseOrder) {
        logger.info("Calling sp_updatePOtoConsumption for restaurant_id = [{}], store_id = [{}] and purchaseOrder_Id = [{}].", newPurchaseOrder.getRestaurantId(), newPurchaseOrder.getStoreId(), newPurchaseOrder.getPurchaseOrderId());
        String result = purchaseOrderRepository.updatePOtoConsumption(newPurchaseOrder.getRestaurantId(), newPurchaseOrder.getStoreId(), newPurchaseOrder.getPurchaseOrderId() + "");
        logger.info("Calling sp_updatePOtoConsumption result = [{}] for purchaseOrder_Id = [{}].", result, newPurchaseOrder.getPurchaseOrderId());
    }

    public PurchaseOrder savePurchaseOrderStatus(PurchaseOrderStatusRequest request) {
        int result = purchaseOrderRepository.savePurchaseOrderStatus(request.getPurchaseOrderId(), request.getItemStatus(), request.getUpdatedBy(), new Date());
        logger.info("savePurchaseOrderStatus result = [{}]", result);
        return purchaseOrderRepository.getPurchaseOrderById(request.getPurchaseOrderId());
    }

    public List<PurchaseOrderWithName> getAllPurchaseOrders() {
//        return purchaseOrderRepository.findAll();
        return namedParameterJdbcTemplate.query(PURCHASE_ORDER_ALL, BeanPropertyRowMapper.newInstance(PurchaseOrderWithName.class));
    }

    public List<PurchaseOrder> getAllActivePurchaseOrders() {
        return purchaseOrderRepository.getPurchaseOrdersByStatus(ACTIVE);
    }

    public List<PurchaseOrderWithName> getPurchaseOrdersByStatus(String status) {
//        return purchaseOrderRepository.getPurchaseOrdersByStatus(status);
        return getPurchaseOrderWithName(status, PURCHASE_ORDER_WITH_NAME_STATUS);
    }

    public List<PurchaseOrderWithName> getPurchaseOrdersExcludingStatus(String status) {
//        return purchaseOrderRepository.getPurchaseOrdersExcludingStatus(status);
        return getPurchaseOrderWithName(status, PURCHASE_ORDER_WITH_NAME_STATUS_EXCLUDING);
    }

    List<PurchaseOrderWithName> getPurchaseOrderWithName(String status, String sql) {
        SqlParameterSource parameters = new MapSqlParameterSource("status", status);
        List<PurchaseOrderWithName> purchaseOrderWithNames = namedParameterJdbcTemplate.query(
                sql, parameters, BeanPropertyRowMapper.newInstance(PurchaseOrderWithName.class));
        return purchaseOrderWithNames;
    }
}
