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

import static com.hangaries.constants.HangariesConstants.STATUS_N;

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
        Store store = storeService.addStore(newStore);
        return new ResponseEntity<Store>(store, HttpStatus.OK);

    }

    @PostMapping("saveStore")
    public ResponseEntity<Store> saveStore(@Valid @RequestBody Store newStore) {
        logger.info("Save Store details  : " + newStore.toString());
        Store store = storeService.addStore(newStore);
        return new ResponseEntity<Store>(store, HttpStatus.OK);

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

    @PostMapping("updateStoreActiveFlag")
    List<Store> updateStoreActiveFlag(@RequestParam("storeId") String storeId, @RequestParam("storeActiveFlag") String storeActiveFlag) {
        logger.info("Updating store store active flag status = {} for store id {}. ", storeActiveFlag, storeId);
        return storeService.updateStoreActiveFlag(storeId, storeActiveFlag);

    }

    @PostMapping("deleteStoreByStoreId")
    List<Store> deleteStoreByStoreId(@RequestParam("storeId") String storeId) {
        logger.info("Updating store active flag status = N for store id {}. ", storeId);
        return storeService.updateStoreActiveFlag(storeId, STATUS_N);

    }
}
