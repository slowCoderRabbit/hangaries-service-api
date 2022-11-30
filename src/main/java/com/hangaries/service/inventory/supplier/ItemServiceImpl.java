package com.hangaries.service.inventory.supplier;

import com.hangaries.model.Item;
import com.hangaries.model.inventory.request.ItemStatusRequest;
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

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> getItemsByStatus(String status) {
        return itemRepository.getItemsByStatus(status);
    }

    public Item saveItemStatus(ItemStatusRequest request) {
        int result = itemRepository.saveItemOrderStatus(request.getItemId(), request.getItemStatus(), request.getUpdatedBy(), new Date());
        logger.info("savePurchaseOrderStatus result = [{}]", result);
        return itemRepository.getItemById(request.getItemId());
    }
}
