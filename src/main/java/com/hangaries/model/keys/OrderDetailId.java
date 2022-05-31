package com.hangaries.model.keys;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderDetailId implements Serializable {
    private String orderId;
    private String productId;
    private String subProductId;
}
