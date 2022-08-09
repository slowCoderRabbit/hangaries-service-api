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

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "REPORT_CASHIER_SUMMARY")
public class ReportCashierSummery implements Serializable {

    @Id
    @Column(name = "cashier_name")
    private String cashierName;

    @Column(name = "restaurant_id")
    private String restaurantId;

    @Column(name = "store_id")
    private String storeId;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "category")
    private String category;

    @Column(name = "type_of_data")
    private String typeOfData;

    @Column(name = "total_qty")
    private int totalQty;

    @Column(name = "total_price")
    private float totalPrice;

}
