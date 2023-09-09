package com.hangaries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CSSRequest {

    private String restaurantId;
    private String storeId;
    private String category;
    private String subCategory;

}
