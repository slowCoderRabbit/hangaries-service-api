package com.hangaries.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.hangaries.constants.HangariesConstants.SYSTEM;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ORDER_MASTER")
public class Order implements Serializable {

    @OneToMany(targetEntity = OrderDetail.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    List<OrderDetail> orderDetails = new ArrayList<>();
    @Id
    @Column(name = "order_id")
    private @NotBlank String orderId;

    @Column(name = "external_order_id")
    private String externalOrderId;
    @Column(name = "aggregator_order_id")
    private String aggregatorOrderId;
    @Column(name = "order_channel")
    private String orderChannel;

    @Column(name = "restaurant_id")
    private @NotBlank String restaurantId;
    @Column(name = "store_Id")
    private @NotBlank String storeId;
    @Column(name = "order_source")
    private @NotBlank String orderSource;
    @Column(name = "customer_id")
    private @NotNull Long customerId;
    @Column(name = "order_received_date_time")
    private @NotNull Date orderReceivedDateTime;
    @Column(name = "order_delivery_type")
    private @NotBlank String orderDeliveryType;
    @Column(name = "store_table_id")
    private String storeTableId;
    @Column(name = "order_status")
    private @NotBlank String orderStatus;
    @Column(name = "payment_status")
    private @NotBlank String paymentStatus;
    @Column(name = "payment_mode")
    private @NotBlank String paymentMode;
    @Column(name = "payment_txn_reference")
    private String paymentTxnReference;
    @Column(name = "tax_rule_id")
    private int taxRuleId;
    @Column(name = "total_price")
    private float totalPrice;
    @Column(name = "cgst_calculated_value")
    private float cgstCalculatedValue;
    @Column(name = "sgst_calculated_value")
    private float sgstCalculatedValue;
    @Column(name = "delivery_charges")
    private float deliveryCharges;

    @Column(name = "packaging_charges")
    private float packagingCharges;

    @Column(name = "coupon_code")
    private String couponCode;
    @Column(name = "discount_percentage")
    private Float discountPercentage;
    @Column(name = "overall_price_with_tax")
    private float overallPriceWithTax;

    @Column(name = "discount_amount")
    private float discountAmount;

    @Column(name = "customer_address_id")
    private long customerAddressId;

    @Column(name = "customer_name")
    private String customerName;
    @Column(name = "customer_address")
    private String customerAddress;
    @Column(name = "customer_mobile_number")
    private String customerMobileNumber;

    @Column(name = "delivery_user_id")
    private String deliveryUserId;
    @Column(name = "food_packaged_flag")
    private String foodPackagedFlag = "N";
    @Column(name = "Created_by")
    private String createdBy = SYSTEM;
    @Column(name = "Created_date")
    private Date createdDate = new Date();
    @Column(name = "Updated_by")
    private String updatedBy = SYSTEM;
    @Column(name = "Updated_date")
    private Date updatedDate = new Date();


}
