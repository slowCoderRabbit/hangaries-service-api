package com.hangaries.repository;

import com.hangaries.model.TaxMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface TaxMasterRepository extends JpaRepository<TaxMaster, Long> {

    List<TaxMaster> findByTaxRuleId(String taxId);
    
}