package com.hangaries.model.wera.request;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class WeraOrder {

    private int order_id;
    private int restaurant_id;
    private String restaurant_name;
    private String external_order_id;
    private String order_from;
    private String order_date_time;
    private int enable_delivery;
    private float net_amount;
    private float gross_amount;
    private String payment_mode;
    private String order_type;
    private String order_instructions;
    private float cgst;
    private float sgst;
    private float order_packaging;
    private float order_packaging_cgst;
    private float order_packaging_sgst;
    private float order_packaging_cgst_percent;
    private float order_packaging_sgst_percent;
    private float discount;
    private float delivery_charge;
    private WeraCustomerDetails customer_details;
    private List<WeraOrderItem> order_items;
    private List<WeraOrderDiscount> item_discounts;
    private List<WeraOrderVariant> variants;
    private List<WeraOrderAddon> addons;

}
