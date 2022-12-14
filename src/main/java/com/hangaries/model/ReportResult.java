package com.hangaries.model;

import com.hangaries.model.vo.OrderVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResult {

    List<RSSDate> salesSummeryByDateList;
    List<RSSDishType> salesSummeryByDishType;
    List<RSSOrderSource> salesSummeryByOrderSource;
    List<RSSPaymentMode> salesSummeryByPaymentMode;
    List<ReportDishConsumptionSummary> reportDishConsumptionSummary;
    List<ReportCashierSummeryResponse> reportCashierSummery;
    List<OrderVO> reportOrderData;
    List<RDSReport> reportDashboardSummery;
    List<OrderVO> reportCancelledOrderData;
    List<CustomerReportData> reportCustomerData;


}
