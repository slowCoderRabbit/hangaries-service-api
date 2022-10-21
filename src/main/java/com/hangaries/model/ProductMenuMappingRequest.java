package com.hangaries.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductMenuMappingRequest {

    private String menuAvailable;
    private String productId;
    private String storeId;

}
