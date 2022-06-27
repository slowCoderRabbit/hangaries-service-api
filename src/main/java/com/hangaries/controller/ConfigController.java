package com.hangaries.controller;

import com.hangaries.model.ConfigMaster;
import com.hangaries.service.config.impl.ConfigServiceImpl;
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

}
