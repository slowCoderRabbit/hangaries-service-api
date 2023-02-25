package com.hangaries.model;

import lombok.*;

import javax.persistence.Column;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ReportCancelledOrder {

    @Column(name = "user_login_id")
    private String userLoginId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "restaurant_id")
    private String restaurantId;

    @Column(name = "store_id")
    private String storeId;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "order_source")
    private String orderSource;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "order_delivery_type")
    private String orderDeliveryType;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Column(name = "payment_mode")
    private String paymentMode;

    @Column(name = "delivery_user_id")
    private String deliveryUserId;

    @Column(name = "overall_price_with_tax")
    private Float overallPriceWithTax;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;


}
