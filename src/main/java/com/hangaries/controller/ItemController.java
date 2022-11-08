package com.hangaries.controller;

import com.hangaries.model.Item;
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
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = new ArrayList<>();
        logger.info("Getting list of all items .");
        items = itemService.getAllItems();
        logger.info("[{}] items found.", items.size());
        return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
    }

    @GetMapping("getItemsByStatus")
    public ResponseEntity<List<Item>> getItemByStatus(@RequestParam String status) {
        List<Item> items = new ArrayList<>();
        logger.info("Getting list of all items with status = [{}].", status);
        items = itemService.getItemsByStatus(status);
        logger.info("[{}] [{}] items found.", items.size(), status);
        return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
    }

    @PostMapping("saveItem")
    public Item savePurchaseOrder(@RequestBody Item item) {
        logger.info("Saving item with details = [{}].", item);
        return itemService.saveItem(item);
    }

    @PostMapping("saveItemStatus")
    public Item saveItemStatus(@RequestBody ItemStatusRequest request) {
        logger.info("Saving item status = [{}] for id = [{}].", request.getItemStatus(), request.getId());
        return itemService.saveItemStatus(request);
    }
}
