package com.hangaries.repository;

import com.hangaries.model.ReportDishConsumptionSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RDCSummaryRepository extends JpaRepository<ReportDishConsumptionSummary, Long> {

    @Query(value = "select * from REPORT_DISH_CONSUMPTION_SUMMARY", nativeQuery = true)
    List<ReportDishConsumptionSummary> getReport();
}
