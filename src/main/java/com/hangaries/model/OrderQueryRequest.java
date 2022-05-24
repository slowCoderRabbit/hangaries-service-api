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
    private String orderStatus;
    private String paymentStatus;
    private String mobileNumber;
}
