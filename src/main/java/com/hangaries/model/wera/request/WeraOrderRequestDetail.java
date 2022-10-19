package com.hangaries.model.wera.request;

import com.hangaries.model.Order;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class WeraOrderRequestDetail {

    private boolean weraOrder;
    private String merchant_id;
    private String order_id;
    private List<Order> orders;

}
