package com.hangaries.model.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

import static com.hangaries.constants.HangariesConstants.SYSTEM;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

public class OrderMenuIngredientAddressDTO implements Serializable {

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
    private String foodPackagedFlag;
    private String createdBy = SYSTEM;
    private Date createdDate = new Date();
    private String updatedBy = SYSTEM;
    private Date updatedDate = new Date();
    private String productId;
    private String dishType;
    private String ingredientType;
    private String mobileNumber;
    private String subProductId;
    private Integer quantity;
    private Float price;
    private String remarks;
    private String address;
    private String orderDetailStatus;
    private String deliveryUserId;
    private String kdsRoutingName;
    private String couponCode;
    private Float discountPercentage;
    private String orderDetailFoodPackagedFlag;
    private Float discountAmount;
    private Float packagingCharges;

}
