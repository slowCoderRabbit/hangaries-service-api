package com.hangaries.model.wera.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class WeraOrderResponse {

    private int status;
    private String message;
    private String order_id;
}
