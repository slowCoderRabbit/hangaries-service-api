package com.hangaries.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RDSReport {

    private long request_id;
    private String restaurant_id;
    private String store_id;
    private double total_sales;
    private long total_orders;
    private float avg_order_value;
    private long orders_not_paid;
    private double outstanding_amount;
    private long orders_cancelled;
    private double cancelled_amount;
    private String user_login_id;
    private List<RDDReport> reportDashboardDetails;

}
