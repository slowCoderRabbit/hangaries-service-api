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
@Table(name = "REPORT_SALES_SUMMARY_BY_DISH_TYPE")
public class RSSDishType {

    @Column(name = "restaurant_id")
    private String restaurantId;

    @Column(name = "store_id")
    private String storeId;

    @Id
    @Column(name = "product_id")
    private String productId;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "month")
    private String month;

    @Column(name = "year")
    private Integer year;

    @Column(name = "dish_type")
    private String dishType;

    @Column(name = "total_qty")
    private Integer totalQty;

    @Column(name = "percentage_of_total_qty")
    private Float percentageOfTotalQty;

    @Column(name = "order_value")
    private Float orderValue;

    @Transient
    private String reportName = "SalesSummeryByDishType";


}
