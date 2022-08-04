package com.hangaries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BusinessDateRequest {

    private @NotBlank String restaurantId;
    private @NotBlank String storeId;
    private @NotBlank String businessDate;

}
