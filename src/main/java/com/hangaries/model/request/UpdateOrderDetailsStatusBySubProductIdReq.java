package com.hangaries.model.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UpdateOrderDetailsStatusBySubProductIdReq {

    String orderId;
    String status;
    String productId;
    String subProductId;
    String updatedBy;
}
