package com.hangaries.controller;

import com.hangaries.model.Store;
import com.hangaries.service.store.impl.StoreServiceImpl;
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
public class StoreController {

    private static final Logger logger = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    private StoreServiceImpl storeService;

    @GetMapping("getAllStores")
    public ResponseEntity<List<Store>> getAllStores() {
        logger.info("Getting list of all Store details...");

        List<Store> storeList = new ArrayList<>();
        try {
            storeList = storeService.getAllStores();
            return new ResponseEntity<List<Store>>(storeList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting store details::" + ex.getMessage());
            return new ResponseEntity<List<Store>>(storeList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getStoreDetailsByStoreId")
    public ResponseEntity<List<Store>> getStoreByStoreId(@RequestParam("storeId") String storeId) {
        List<Store> storeList = new ArrayList<>();
        try {
            logger.info("Get store details for store id = {}.", storeId);
            storeList = storeService.getStoreDetailsByStoreId(storeId);
            return new ResponseEntity<List<Store>>(storeList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting store details::" + ex.getMessage());
            return new ResponseEntity<List<Store>>(storeList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
