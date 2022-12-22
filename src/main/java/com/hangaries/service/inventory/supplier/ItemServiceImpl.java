package com.hangaries.service.inventory.supplier;

import com.hangaries.model.Item;
import com.hangaries.model.ItemConsumptionSummery;
import com.hangaries.model.inventory.request.ItemStatusRequest;
import com.hangaries.repository.inventory.ItemConsumptionSummeryRepository;
import com.hangaries.repository.inventory.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemConsumptionSummeryRepository itemConsumptionSummeryRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item saveItem(Item item) {
        Item newItem = itemRepository.save(item);
        String result = itemRepository.populateItemConsumption(newItem.getItemId());
        logger.info("Calling sp_populateItemConsumption result = [{}] for ItemId = [{}].", result, newItem.getItemId());
        return newItem;
    }

    public List<Item> getItemsByStatus(String status) {
        return itemRepository.getItemsByStatus(status);
    }

    public Item saveItemStatus(ItemStatusRequest request) {
        int result = itemRepository.saveItemOrderStatus(request.getItemId(), request.getItemStatus(), request.getUpdatedBy(), new Date());
        logger.info("savePurchaseOrderStatus result = [{}]", result);
        return itemRepository.getItemById(request.getItemId());
    }

    public List<ItemConsumptionSummery> getItemConsumptionSummery() {
        return itemConsumptionSummeryRepository.findAll();
    }

    public ItemConsumptionSummery saveItemConsumptionSummery(ItemConsumptionSummery item) {
        return itemConsumptionSummeryRepository.save(item);
    }


//    public String performInventoryUpdateEOD(String restaurantId, String storeId) {
//        return itemConsumptionSummeryRepository.inventoryUpdateEOD(restaurantId, storeId);
//    }

    public List<ItemConsumptionSummery> saveAllItemConsumptionSummery(List<ItemConsumptionSummery> items) {
        return itemConsumptionSummeryRepository.saveAll(items);
    }

    public Item getItemByItemId(long itemId) {
        return itemRepository.getItemById(itemId);
    }
}
