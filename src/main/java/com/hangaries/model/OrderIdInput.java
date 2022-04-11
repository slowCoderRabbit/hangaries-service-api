package com.hangaries.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderIdInput {

    private @NotBlank String restaurantId;
    private @NotBlank String storeId;
    private @NotBlank String orderSource;


}
