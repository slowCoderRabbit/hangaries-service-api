package com.hangaries.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderMenuIngredientAddressId implements Serializable {

    private String orderId;
    private String productId;
    private String subProductId;
}
