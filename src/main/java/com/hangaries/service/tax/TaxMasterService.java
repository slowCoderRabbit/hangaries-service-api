package com.hangaries.service.tax;

import com.hangaries.model.TaxMaster;

import java.util.List;

public interface TaxMasterService {
    List<TaxMaster> getAllTaxDetails();

    List<TaxMaster> findByTaxRuleId(String taxRuleId);

    List<TaxMaster> getTaxDetailsByRestroAndStore(String restaurantId, String storeId);
}
