package com.hangaries.repository;

import com.hangaries.model.RSSPaymentMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RSSPayModeRepository extends JpaRepository<RSSPaymentMode, Long> {

    @Query(value = "select * from REPORT_SALES_SUMMARY_BY_PAYMENT_MODE", nativeQuery = true)
    List<RSSPaymentMode> getReport();

}
