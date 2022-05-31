package com.hangaries.model.keys;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderProcessingDetailsId implements Serializable {

    private String orderId;
    private String restaurantId;
    private String storeId;
    private String orderStatus;
}
