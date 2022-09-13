package com.hangaries.model;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderQueryRequest {

    private String orderId;
    private String restaurantId;
    private String storeId;
    private String orderReceivedDate;
    private String orderReceivedFromDate;
    private String orderStatus;
    private String paymentStatus;
    private String mobileNumber;
    private String orderDeliveryType;
    private String deliveryUserId;
    private String foodPackagedFlag;
    private String orderSource;

}
