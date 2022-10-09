package com.hangaries.model.wera.request;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class WeraOrderItem {

    private int wera_item_id;
    private String item_id;
    private String item_name;
    private float item_unit_price;
    private float subtotal;
    private float discount;
    private int item_quantity;
    private float cgst;
    private float sgst;
    private float cgst_percent;
    private float sgst_percent;
    private float packaging;
    private float packaging_cgst;
    private float packaging_sgst;
    private float packaging_cgst_percent;
    private float packaging_sgst_percent;
    private List<WeraOrderDiscount> item_discounts;
    private List<WeraOrderVariant> variants;
    private List<WeraOrderAddon> addons;

}
