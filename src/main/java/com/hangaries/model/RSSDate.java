package com.hangaries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "REPORT_SALES_SUMMARY_BY_DATE")
public class RSSDate {

    @Column(name = "restaurant_id")
    private String restaurantId;

    @Column(name = "store_id")
    private String storeId;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "no_of_orders")
    private Integer noOfOrders;

    @Id
    @Column(name = "order_value")
    private Float orderValue;

}
