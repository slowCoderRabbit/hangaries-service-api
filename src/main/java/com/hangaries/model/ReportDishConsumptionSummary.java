package com.hangaries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "REPORT_DISH_CONSUMPTION_SUMMARY")
public class ReportDishConsumptionSummary implements Serializable {

    @Column(name = "restaurant_id")
    private String restaurantId;

    @Column(name = "store_id")
    private String storeId;

    @Id
    @Column(name = "dish_id")
    private String dishId;

    @Column(name = "dish_name")
    private String dishName;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "size")
    private String size;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "total_qty")
    private Integer totalQty;

    @Column(name = "total_price")
    private Float totalPrice;


}
