package com.hangaries.controller;

import com.hangaries.model.Item;
import com.hangaries.model.ItemConsumptionSummery;
import com.hangaries.model.inventory.request.ItemStatusRequest;
import com.hangaries.service.inventory.supplier.ItemServiceImpl;
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
public class ItemController {

    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private ItemServiceImpl itemService;

    @GetMapping("getAllItems")
    public ResponseEntity<List<Item>> getAllItems(@RequestParam("restaurantId") String restaurantId,
                                                  @RequestParam("storeId") String storeId) {
        List<Item> items = new ArrayList<>();
        logger.info("Getting list of all items for restaurantId = [{}], storeId = [{}].", restaurantId, storeId);
        items = itemService.getAllItems(restaurantId, storeId);
        logger.info("[{}] items found.", items.size());
        return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
    }

    @GetMapping("getItemsByStatus")
    public ResponseEntity<List<Item>> getItemByStatus(@RequestParam("restaurantId") String restaurantId,
                                                      @RequestParam("storeId") String storeId, @RequestParam String status) {
        List<Item> items = new ArrayList<>();
        logger.info("Getting list of all items for restaurantId = [{}], storeId = [{}] with status = [{}].", restaurantId, storeId, status);
        items = itemService.getItemsByStatus(restaurantId, storeId, status);
        logger.info("[{}] [{}] items found.", items.size(), status);
        return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
    }

    @PostMapping("saveItem")
    public Item saveItem(@RequestBody Item item) {
        logger.info("Saving item with details = [{}].", item);
        return itemService.saveItem(item);
    }

    @PostMapping("saveItemStatus")
    public Item saveItemStatus(@RequestBody ItemStatusRequest request) {
        logger.info("Saving item status = [{}] for id = [{}].", request.getItemStatus(), request.getItemId());
        return itemService.saveItemStatus(request);
    }

    @GetMapping("getItemConsumptionSummery")
    public ResponseEntity<List<ItemConsumptionSummery>> getItemConsumptionSummery() {
        List<ItemConsumptionSummery> items = new ArrayList<>();
        logger.info("Getting Item Consumption Summery.");
        items = itemService.getItemConsumptionSummery();
        logger.info("[{}] items found.", items.size());
        return new ResponseEntity<List<ItemConsumptionSummery>>(items, HttpStatus.OK);
    }

    @PostMapping("saveItemConsumptionSummery")
    public ItemConsumptionSummery saveItemConsumptionSummery(@RequestBody ItemConsumptionSummery item) {
        logger.info("Saving Item Consumption Summery with details = [{}].", item);
        return itemService.saveItemConsumptionSummery(item);
    }

    @PostMapping("saveAllItemConsumptionSummery")
    public List<ItemConsumptionSummery> saveAllItemConsumptionSummery(@RequestBody List<ItemConsumptionSummery> items) {
        logger.info("Saving All Item Consumption Summery of size = [{}] ", items.size());
        return itemService.saveAllItemConsumptionSummery(items);
    }

    @GetMapping("getItemByItemId")
    public ResponseEntity<Item> getItemByItemId(@RequestParam long itemId) {
        logger.info("Getting item for item id = [{}].", itemId);
        Item item = itemService.getItemByItemId(itemId);
        logger.info("[{}] item found!!", item);
        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }

    @GetMapping("getItemByItemCategory")
    public ResponseEntity<List<Item>> getItemByItemCategory(@RequestParam("category") String category) {
        logger.info("Getting item for item category = [{}].", category);
        List<Item> result = itemService.getItemByItemCategory(category);
        logger.info("[{}] items found for category = [{}].", result.size(), category);
        return new ResponseEntity<List<Item>>(result, HttpStatus.OK);
    }

}
