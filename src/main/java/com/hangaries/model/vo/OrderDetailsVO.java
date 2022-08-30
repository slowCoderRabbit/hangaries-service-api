package com.hangaries.model.vo;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderDetailsVO {

    private String orderId;
    private String productId;
    private String productName;
    private String subProductId;
    private String ingredient;
    private Integer quantity;
    private Float price;
    private String remarks;
    private String orderDetailStatus;
    private String kdsRoutingName;
    private String orderDetailFoodPackagedFlag;
}
