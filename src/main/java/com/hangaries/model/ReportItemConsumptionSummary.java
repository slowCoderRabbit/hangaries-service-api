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
@Table(name = "REPORT_ITEM_CONSUMPTION_SUMMARY")
public class ReportItemConsumptionSummary implements Serializable {

    @Id
    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "restaurant_id")
    private String restaurantId;

    @Column(name = "store_id")
    private String storeId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_category")
    private String itemCategory;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "opng_qty")
    private Double opngQty;

    @Column(name = "item_ordered")
    private Double itemOrdered;

    @Column(name = "item_wasted")
    private Double itemWasted;

    @Column(name = "item_consumed")
    private Double itemConsumed;

    @Column(name = "item_variance")
    private Double itemVariance;

    @Column(name = "item_amount")
    private Double itemAmount;

    @Column(name = "eod_qty")
    private Double eodQty;


}
