package com.hangaries.model.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UpdateOrderDetailsStatusByOrderIdReq {
    String orderId;
    String status;
    String updatedBy;
}
