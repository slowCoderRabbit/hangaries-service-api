package com.hangaries.controller;

import com.hangaries.model.Store;
import com.hangaries.service.store.impl.StoreServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class StoreController {

    private static final Logger logger = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    private StoreServiceImpl storeService;

    @PostMapping("addStore")
    public ResponseEntity<Store> addStore(@Valid @RequestBody Store newStore) {
        logger.info("New Store details  : " + newStore.toString());
        Store store = new Store();
        try {
            store = storeService.addStore(newStore);
            return new ResponseEntity<Store>(store, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting store details::" + ex.getMessage());
            return new ResponseEntity<Store>(store, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

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

    @DeleteMapping("deleteStoreByStoreId")
    ResponseEntity<String> deleteStoreByStoreId(@RequestParam("storeId") String storeId) {
        logger.info("Deleting store details for store id {}. ", storeId);
        try {
            storeService.deleteStoreByStoreId(storeId);
            return new ResponseEntity<String>("Success", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<String>("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
