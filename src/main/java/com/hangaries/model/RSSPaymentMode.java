package com.hangaries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "REPORT_SALES_SUMMARY_BY_PAYMENT_MODE")
public class RSSPaymentMode {

    @Column(name = "restaurant_id")
    private String restaurantId;

    @Column(name = "store_id")
    private String storeId;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "month")
    private String month;

    @Column(name = "year")
    private Integer year;

    @Column(name = "no_of_orders")
    private Integer noOfOrders;

    @Column(name = "payment_mode")
    private String paymentMode;

    @Id
    @Column(name = "order_value")
    private Float orderValue;

    @Transient
    private String reportName = "SalesSummeryByPaymentMode";


}
