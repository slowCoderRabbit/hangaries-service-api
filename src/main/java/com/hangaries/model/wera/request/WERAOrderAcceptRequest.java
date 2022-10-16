package com.hangaries.model.wera.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class WERAOrderAcceptRequest {

    private String merchant_id;
    private String order_id;
    private int preparation_time;

}
