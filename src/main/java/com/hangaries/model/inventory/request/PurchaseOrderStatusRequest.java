package com.hangaries.model.inventory.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PurchaseOrderStatusRequest {

    private long purchaseOrderId;
    private String itemStatus;
    private String updatedBy;
}
