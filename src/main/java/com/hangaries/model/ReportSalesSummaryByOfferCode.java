package com.hangaries.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ReportSalesSummaryByOfferCode implements Serializable {

    private String user_login_id;
    private String restaurant_id;
    private String store_id;
    private String restaurant_name;
    private String coupon_code;
    private String offer_description;
    private int no_of_orders;
    private float order_value;


}
