package com.hangaries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DishToppingResponse {

    private int id;
    private String productId;
    private String subProductId;
    private String productName;
    private String subProductName;
    private String restaurantId;
    private String storeId;
    private String restaurantName;
    private String storeName;
    private String ingredientAvailableFlag;


}
