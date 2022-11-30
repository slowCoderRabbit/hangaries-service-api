package com.hangaries.service.inventory.supplier;

import com.hangaries.model.inventory.Supplier;
import com.hangaries.model.inventory.request.SupplierStatusRequest;
import com.hangaries.repository.inventory.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.hangaries.constants.HangariesConstants.ACTIVE;

@Service
public class SupplierServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(SupplierServiceImpl.class);

    @Autowired
    SupplierRepository supplierRepository;

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public List<Supplier> getAllActiveSuppliers() {
        return supplierRepository.getAllActiveSuppliers(ACTIVE);
    }

    public Supplier saveSupplierStatus(SupplierStatusRequest request) {
        int result = supplierRepository.saveSupplierStatus(request.getSupplierId(), request.getSupplierStatus(), request.getUpdatedBy(), new Date());
        logger.info("saveSupplierStatus result = [{}]", result);
        return supplierRepository.getSupplierById(request.getSupplierId());
    }
}
