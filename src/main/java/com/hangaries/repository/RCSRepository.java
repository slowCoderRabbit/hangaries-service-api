package com.hangaries.repository;

import com.hangaries.model.ReportCashierSummery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RCSRepository extends JpaRepository<ReportCashierSummery, Long> {

    @Query(value = "select * from REPORT_CASHIER_SUMMARY", nativeQuery = true)
    List<ReportCashierSummery> getReport();
}
