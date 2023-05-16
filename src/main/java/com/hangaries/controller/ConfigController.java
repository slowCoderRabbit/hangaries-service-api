package com.hangaries.controller;

import com.hangaries.model.*;
import com.hangaries.service.config.impl.ConfigServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ConfigController {

    private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);

    @Autowired
    ConfigServiceImpl configService;

    @GetMapping("getConfigDetailsByCriteria")
    public ResponseEntity<List<ConfigMaster>> getConfigDetailsByCriteria(@RequestParam("restaurantId") String restaurantId,
                                                                         @RequestParam("storeId") String storeId,
                                                                         @RequestParam("criteria") String criteria) {
        logger.info("Get config by criteria for restaurantId = {}, storeId = {}, criteria = {}", restaurantId, storeId, criteria);

        List<ConfigMaster> configMasterList = new ArrayList<>();
        try {
            configMasterList = configService.getConfigDetailsByCriteria(restaurantId, storeId, criteria);
            return new ResponseEntity<List<ConfigMaster>>(configMasterList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error getting config by criteria = {} :: {}", criteria, ex.getMessage());
            return new ResponseEntity<List<ConfigMaster>>(configMasterList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("addConfigDetailsByCriteria")
    public ResponseEntity<ConfigMaster> addConfigDetailsByCriteria(@Valid @RequestBody ConfigMaster configMaster) {
        logger.info("Adding config by criteria details = {}", configMaster);

        ConfigMaster configMasterList = null;
        try {
            configMasterList = configService.addConfigDetailsByCriteria(configMaster);
            return new ResponseEntity<ConfigMaster>(configMasterList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error getting config by criteria = {} :: {}", configMaster, ex.getMessage());
            return new ResponseEntity<ConfigMaster>(configMasterList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getAppDetails")
    public ResponseEntity<List<AppDetails>> getAppDetails(@RequestParam("restaurantId") String restaurantId,
                                                          @RequestParam("storeId") String storeId) {
        logger.info("Get Application details for restaurantId = {}, storeId = {}. ", restaurantId, storeId);

        List<AppDetails> appDetails = null;
        try {
            appDetails = configService.getAppDetails(restaurantId, storeId);
            return new ResponseEntity<List<AppDetails>>(appDetails, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error getting Application details!!!!!!! {}", ex.getMessage());
            return new ResponseEntity<List<AppDetails>>(appDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping("saveBusinessDate")
//    public ResponseEntity<BusinessDate> updateBusinessDate(@RequestBody BusinessDate businessDate) {
//        logger.info("Saving business date = {} for restaurantId = {} and storeId = {}.", businessDate.getBusinessDate(), businessDate.getRestaurantId(), businessDate.getStoreId());
//
//        BusinessDate businessDateNew = configService.updateBusinessDate(businessDate);
//        return new ResponseEntity<BusinessDate>(businessDateNew, HttpStatus.OK);
//
//    }

    @PostMapping("updateBusinessDate")
    public ResponseEntity<BusinessDate> updateBusinessDate(@Valid @RequestBody BusinessDateRequest request) throws ParseException {
        logger.info("Updating business date = [{}] for restaurantId = [{}] and storeId = [{}].", request.getBusinessDate(), request.getRestaurantId(), request.getStoreId());
        BusinessDate businessDate = configService.updateBusinessDate(request);
        logger.info("Business date update to = [{}] for restaurantId = [{}] and storeId = [{}].", businessDate.getBusinessDate(), businessDate.getRestaurantId(), businessDate.getStoreId());
        return new ResponseEntity<BusinessDate>(businessDate, HttpStatus.OK);

    }

    @GetMapping("getBusinessDateByRestroAndStore")
    public ResponseEntity<BusinessDate> getBusinessDateByRestroAndStore(@RequestParam("restaurantId") String restaurantId,
                                                                        @RequestParam("storeId") String storeId) {
        logger.info("Getting business date for restaurantId = [{}] and storeId = [{}].", restaurantId, storeId);

        BusinessDate businessDate = configService.getBusinessDate(restaurantId, storeId);
        logger.info("Business date = [{}] for restaurantId = [{}] and storeId = [{}].", businessDate.getBusinessDate(), businessDate.getRestaurantId(), businessDate.getStoreId());
        return new ResponseEntity<BusinessDate>(businessDate, HttpStatus.OK);

    }

    @GetMapping("getAllBusinessDates")
    public ResponseEntity<List<BusinessDate>> getAllBusinessDates(@RequestParam("restaurantId") String restaurantId) {
        logger.info("Getting all Business Dates for restaurantId = [{}].", restaurantId);

        List<BusinessDate> businessDates = configService.getAllBusinessDates(restaurantId);
        logger.info("Returning [{}] records for business dates.", businessDates.size());
        return new ResponseEntity<List<BusinessDate>>(businessDates, HttpStatus.OK);

    }

    @PostMapping("performEndOfDay")
    public ResponseEntity<BusinessDate> performEndOfDay(@RequestParam("restaurantId") String restaurantId,
                                                        @RequestParam("storeId") String storeId) {
        logger.info("Performing EOD for restaurantId = [{}] and storeId = [{}].", restaurantId, storeId);

        BusinessDate businessDateNew = configService.performEndOfDay(restaurantId, storeId);

        logger.info("Business date update to = [{}] for restaurantId = [{}] and storeId = [{}].", businessDateNew.getBusinessDate(), businessDateNew.getRestaurantId(), businessDateNew.getStoreId());
        return new ResponseEntity<BusinessDate>(businessDateNew, HttpStatus.OK);

    }

    @GetMapping("getPaymentModes")
    public ResponseEntity<List<PaymentMode>> getPaymentModes(@RequestParam("restaurantId") String restaurantId) {
        logger.info("Getting list of payment modes for restaurantId = [{}].", restaurantId);
        List<PaymentMode> paymentModeList = new ArrayList<>();
        paymentModeList = configService.getPaymentModes(restaurantId);
        logger.info("Payment modes list size = [{}].", paymentModeList.size());
        return new ResponseEntity<List<PaymentMode>>(paymentModeList, HttpStatus.OK);

    }


}
