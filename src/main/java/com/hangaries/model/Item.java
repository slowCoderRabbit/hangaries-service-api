package com.hangaries.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

import static com.hangaries.constants.HangariesConstants.SYSTEM;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ITEM_MASTER")
public class Item {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @Column(name = "restaurant_id")
    private String restaurantId;

    @Column(name = "store_id")
    private String storeId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_category")
    private String itemCategory;

    @Column(name = "item_sub_category")
    private String itemSubCategory;

    @Column(name = "item_uom")
    private String itemUom;

    @Column(name = "item_unit_cost")
    private Float itemUnitCost;

    @Column(name = "item_gst_percentage")
    private Float itemGstPercentage;

    @Column(name = "item_tracking_flag")
    private String itemTrackingFlag;

    @Column(name = "item_status")
    private String itemStatus;

    @Column(name = "Created_by")
    private String createdBy = SYSTEM;

    @Column(name = "Created_date")
    private Date createdDate = new Date();

    @Column(name = "Updated_by")
    private String updatedBy = SYSTEM;

    @Column(name = "Updated_date")
    private Date updatedDate = new Date();

}
