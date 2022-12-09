package com.hangaries.model;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class PurchaseOrderWithName extends PurchaseOrder implements Serializable {

    private String restaurantName;
    private String storeName;
    private String supplierName;

}
