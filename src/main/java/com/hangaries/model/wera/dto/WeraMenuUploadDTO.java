package com.hangaries.model.wera.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class WeraMenuUploadDTO {

    @Column(name = "merchant_id")
    private int merchant_id;

    @Column(name = "restaurant_id")
    private String restaurant_id;

    @Column(name = "store_id")
    private String store_id;

    @Column(name = "id")
    private String id;

    @Column(name = "item_name")
    private String item_name;

    @Column(name = "zomato_product_price")
    private float zomato_product_price;

    @Column(name = "swiggy_product_price")
    private float swiggy_product_price;

    @Column(name = "active")
    private String active;

    @Column(name = "packaging")
    private float packaging;

    @Column(name = "zomato_product_cgst")
    private float zomato_product_cgst;

    @Column(name = "zomato_product_sgst")
    private float zomato_product_sgst;

    @Column(name = "swiggy_product_cgst")
    private float swiggy_product_cgst;

    @Column(name = "swiggy_product_sgst")
    private float swiggy_product_sgst;

    @Column(name = "addon_id")
    private String addon_id;
    @Column(name = "addon_name")
    private String addon_name;

    @Column(name = "zomato_subproduct_price")
    private float zomato_subproduct_price;

    @Column(name = "swiggy_subproduct_price")
    private float swiggy_subproduct_price;

    @Column(name = "ingredient_available_flag")
    private String ingredient_available_flag;


}
