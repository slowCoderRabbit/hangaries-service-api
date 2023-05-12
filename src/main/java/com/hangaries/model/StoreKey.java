package com.hangaries.model;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class StoreKey implements Serializable {
    private String restaurantId;
    private String storeId;
}
