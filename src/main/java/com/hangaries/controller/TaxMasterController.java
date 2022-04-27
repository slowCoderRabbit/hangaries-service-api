package com.hangaries.controller;


import com.hangaries.model.TaxMaster;
import com.hangaries.service.tax.impl.TaxMasterServiceImpl;
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
public class TaxMasterController {

    private static final Logger logger = LoggerFactory.getLogger(TaxMasterController.class);

    @Autowired
    private TaxMasterServiceImpl taxMasterService;

    @GetMapping("getAllTaxDetails")
    public ResponseEntity<List<TaxMaster>> getAllTaxDetails() {
        logger.info("Get all tax details...");

        List<TaxMaster> taxRuleDetails = new ArrayList<>();
        try {
            taxRuleDetails = taxMasterService.getAllTaxDetails();
            return new ResponseEntity<List<TaxMaster>>(taxRuleDetails, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting tax rule details::" + ex.getMessage());
            return new ResponseEntity<List<TaxMaster>>(taxRuleDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getTaxDetailsByTaxRuleId")
    public ResponseEntity<List<TaxMaster>> getTaxDetailsByTaxRuleId(@RequestParam("taxRuleId") String taxRuleId) {
        List<TaxMaster> taxRuleDetails = new ArrayList<>();
        try {
            logger.info("Get tax details by tax rule id = {}.", taxRuleId);
            taxRuleDetails = taxMasterService.findByTaxRuleId(taxRuleId);
            return new ResponseEntity<List<TaxMaster>>(taxRuleDetails, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting tax rule details::" + ex.getMessage());
            return new ResponseEntity<List<TaxMaster>>(taxRuleDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getTaxDetailsByRestroAndStore")
    public ResponseEntity<List<TaxMaster>> getTaxDetailsByRestroAndStore(@RequestParam("restaurantId") String restaurantId, @RequestParam("storeId") String storeId) {
        List<TaxMaster> taxRuleDetails = new ArrayList<>();
        try {
            logger.info("Get tax details by restaurantId = {} and storeId = {}.", restaurantId,storeId);
            taxRuleDetails = taxMasterService.getTaxDetailsByRestroAndStore(restaurantId,storeId);
            return new ResponseEntity<List<TaxMaster>>(taxRuleDetails, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting tax rule details::" + ex.getMessage());
            return new ResponseEntity<List<TaxMaster>>(taxRuleDetails, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
