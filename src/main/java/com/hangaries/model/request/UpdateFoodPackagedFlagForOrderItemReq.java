package com.hangaries.model.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UpdateFoodPackagedFlagForOrderItemReq {

    String orderId;
    String productId;
    String subProductId;
    String foodPackagedFlag;
    String updatedBy;
}
