package com.hangaries.model.wera.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class WeraOrderDiscount {
    private String type;
    private float amount;
}
