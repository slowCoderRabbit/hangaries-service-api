package com.hangaries.repository;

import com.hangaries.model.RootEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<RootEntity, Integer> {

    @Procedure(procedureName = "sp_orderSummary", outputParameterName = "oErrorDescription")
    String getReports(@Param("restaurantId") String restaurantId, @Param("storeId") String storeId, @Param("fromDate") String fromDate, @Param("toDate") String toDate,@Param("reportName") String reportName, @Param("userLoginId") String userLoginId);

}
