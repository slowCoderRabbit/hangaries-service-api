package com.hangaries.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CashierSummeryData {

    private String typeOfData;
    private int totalQty;
    private float totalPrice;
    private String category;
}
