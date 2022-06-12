package com.hangaries.controller;

import com.hangaries.model.ModuleMaster;
import com.hangaries.service.module.impl.ModuleServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ModuleController {
    private static final Logger logger = LoggerFactory.getLogger(ModuleController.class);

    @Autowired
    ModuleServiceImpl moduleService;

    @GetMapping("getAllModule")
    public ResponseEntity<List<ModuleMaster>> getAllModule() {
        logger.info("Getting list of all Modules...");

        List<ModuleMaster> moduleList = new ArrayList<>();
        try {
            moduleList = moduleService.getAllModule();
            return new ResponseEntity<List<ModuleMaster>>(moduleList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting store details::" + ex.getMessage());
            return new ResponseEntity<List<ModuleMaster>>(moduleList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getModuleByRestroAndStore")
    public ResponseEntity<List<ModuleMaster>> getModuleByRestroAndStore(@RequestParam("restaurantId") @NotBlank String restaurantId, @RequestParam("storeId") @NotBlank String storeId) {
        logger.info("Getting list of Modules for restaurantId = [{}] and storeId = [{}].", restaurantId, storeId);

        List<ModuleMaster> moduleList = new ArrayList<>();
        try {
            moduleList = moduleService.getModuleByRestroAndStore(restaurantId, storeId);
            return new ResponseEntity<List<ModuleMaster>>(moduleList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting store details::" + ex.getMessage());
            return new ResponseEntity<List<ModuleMaster>>(moduleList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
