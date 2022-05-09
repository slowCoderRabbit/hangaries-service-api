package com.hangaries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResult {

    List<RSSDate> salesSummeryByDateList;
    List<RSSDishType> SalesSummeryByDishType;
    List<RSSOrderSource> SalesSummeryByOrderSource;
    List<RSSPaymentMode> SalesSummeryByPaymentMode;


}
