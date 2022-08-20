package com.hangaries.model;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ReportCashierSummeryResponse {

    private String cashierName;
    private String restaurantId;
    private String storeId;
    private String storeName;
    private Map<String, List<CashierSummeryData>> details;

}
