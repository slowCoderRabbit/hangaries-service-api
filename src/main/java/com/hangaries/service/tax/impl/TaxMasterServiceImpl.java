package com.hangaries.service.tax.impl;

import com.hangaries.model.TaxMaster;
import com.hangaries.repository.TaxMasterRepository;
import com.hangaries.service.tax.TaxMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxMasterServiceImpl implements TaxMasterService {

    @Autowired
    TaxMasterRepository taxMasterRepository;

    @Override
    public List<TaxMaster> getAllTaxDetails() {
        return taxMasterRepository.findAll();
    }

    @Override
    public List<TaxMaster> findByTaxRuleId(String taxRuleId) {
        return taxMasterRepository.findByTaxRuleId(taxRuleId);
    }

    @Override
    public List<TaxMaster> getTaxDetailsByRestroAndStore(String restaurantId, String storeId) {
        return taxMasterRepository.getTaxDetailsByRestroAndStore( restaurantId, storeId);
    }
}