package com.hangaries.model.vo;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderVO {

    private String orderId;
    private String restaurantId;
    private String restaurantName;
    private String storeId;
    private String storeName;
    private String orderSource;
    private Integer customerId;
    private String customerName;
    private Date orderReceivedDateTime;
    private String orderDeliveryType;
    private String storeTableId;
    private String orderStatus;
    private String paymentStatus;
    private String paymentMode;
    private String paymentTxnReference;
    private Integer taxRuleId;
    private Float totalPrice;
    private Float cgstCalculatedValue;
    private Float sgstCalculatedValue;
    private Float deliveryCharges;
    private Float overallPriceWithTax;
    private Long customerAddressId;
    private String mobileNumber;
    private String address;
    private String deliveryUserId;
    private String foodPackagedFlag;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private List<OrderDetailsVO> orderDetails;
}
