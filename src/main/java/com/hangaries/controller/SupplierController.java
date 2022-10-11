package com.hangaries.controller;

import com.hangaries.model.inventory.Supplier;
import com.hangaries.model.inventory.request.SupplierStatusRequest;
import com.hangaries.service.inventory.supplier.SupplierServiceImpl;
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
public class SupplierController {

    private static final Logger logger = LoggerFactory.getLogger(SupplierController.class);

    @Autowired
    private SupplierServiceImpl supplierService;

    @GetMapping("getAllSuppliers")
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        logger.info("Getting list of all suppliers.");
        suppliers = supplierService.getAllSuppliers();
        logger.info("[{}] suppliers found.", suppliers.size());
        return new ResponseEntity<List<Supplier>>(suppliers, HttpStatus.OK);
    }

    @GetMapping("getAllActiveSuppliers")
    public ResponseEntity<List<Supplier>> getAllActiveSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();
        logger.info("Getting list of all active suppliers.");
        suppliers = supplierService.getAllActiveSuppliers();
        logger.info("[{}] active suppliers found.", suppliers.size());
        return new ResponseEntity<List<Supplier>>(suppliers, HttpStatus.OK);
    }

    @PostMapping("saveSupplier")
    public Supplier saveSupplier(@RequestBody Supplier supplier) {
        logger.info("Saving suppliers with details = [{}].", supplier);
        return supplierService.saveSupplier(supplier);
    }

    @PostMapping("saveSupplierStatus")
    public Supplier saveSupplierStatus(@RequestBody SupplierStatusRequest request) {
        logger.info("Saving suppliers status = [{}] for supplier id = [{}].", request.getSupplierStatus(), request.getId());
        return supplierService.saveSupplierStatus(request);
    }
}
