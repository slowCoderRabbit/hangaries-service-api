package com.hangaries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

import static com.hangaries.constants.HangariesConstants.SYSTEM;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "ITEM_CONSUMPTION_SUMMARY")
public class ItemConsumptionSummery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "item_id")
    private long itemId;
    @Column(name = "item_name")
    private String itemName;
    @Column(name = "item_uom")
    private String itemUom;
    @Column(name = "restaurant_id")
    private String restaurantId;
    @Column(name = "store_Id")
    private String storeId;
    @Column(name = "business_date")
    private Date businessDate;
    @Column(name = "po_opng_qty")
    private float poOpngQty;
    @Column(name = "po_today_qty")
    private float poTodayQty;
    @Column(name = "po_wastage_qty")
    private float poWastageQty;
    @Column(name = "po_net_qty")
    private float poNetQty;
    @Column(name = "item_curr_consumption_qty")
    private float itemCurrConsumptionQty;
    @Column(name = "item_eod_consumption_qty")
    private float itemEodConsumptionQty;
    @Column(name = "item_consumption_variance_qty")
    private float itemConsumptionVarianceQty;
    @Column(name = "item_consumption_amount")
    private float itemConsumptionAmount;
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "recon_status")
    private String reconStatus;
    @Column(name = "created_by")
    private String createdBy = SYSTEM;
    @Column(name = "created_date")
    private Date createdDate = new Date();
    @Column(name = "updated_by")
    private String updatedBy = SYSTEM;
    @Column(name = "updated_date")
    private Date updatedDate = new Date();

}
