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
@Table(name = "PURCHASE_ORDER")
public class PurchaseOrder {

    @Id
    @Column(name = "purchase_order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchaseOrderId;

    @Column(name = "restaurant_id")
    private String restaurantId;

    @Column(name = "store_id")
    private String storeId;

    @Column(name = "supplier_id")
    private int supplierId;

    @Column(name = "purchase_date")
    private Date purchaseDate;

    @Column(name = "bill_number")
    private String billNumber;

    @Column(name = "item_id")
    private String itemId;

    @Column(name = "purchase_qty")
    private float purchaseQty;

    @Column(name = "wastage_qty")
    private float wastageQty;

    @Column(name = "net_qty")
    private float netQty;

    @Column(name = "quoted_purchase_price")
    private float quotedPurchasePrice;

    @Column(name = "discount_amount")
    private float discountAmount;

    @Column(name = "net_purchase_price")
    private float netPurchasePrice;

    @Column(name = "gst_amount")
    private float gstAmount;

    @Column(name = "purchase_category")
    private String purchaseCategory;

    @Column(name = "purchase_order_status")
    private String purchaseOrderStatus;

    @Column(name = "Created_by")
    private String createdBy = SYSTEM;

    @Column(name = "Created_date")
    private Date createdDate = new Date();

    @Column(name = "Updated_by")
    private String updatedBy = SYSTEM;

    @Column(name = "Updated_date")
    private Date updatedDate = new Date();

}
