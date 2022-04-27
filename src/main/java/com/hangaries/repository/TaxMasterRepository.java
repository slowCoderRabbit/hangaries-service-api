package com.hangaries.repository;

import com.hangaries.model.TaxMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TaxMasterRepository extends JpaRepository<TaxMaster, Long> {

    List<TaxMaster> findByTaxRuleId(String taxId);

    @Query(value = "select * from TAX_MASTER where restaurant_id=:restaurantId and store_id=:storeId and tax_rule_status='ACTIVE'", nativeQuery = true)
    List<TaxMaster> getTaxDetailsByRestroAndStore(String restaurantId, String storeId);
}