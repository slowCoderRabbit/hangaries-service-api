package com.hangaries.model.wera.dto;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

import static com.hangaries.constants.HangariesConstants.SYSTEM;
import static com.hangaries.constants.HangariesConstants.WERA;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "WERA_ORDER_MASTER")
public class WeraOrderMasterDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "order_id")
    private String order_id;

    @Column(name = "wera_order_id")
    private String wera_order_id;

    @Column(name = "external_order_id")
    private String external_order_id;

    @Column(name = "wera_outlet_id")
    private int wera_outlet_id;

    @Column(name = "restaurant_id")
    private String restaurant_id;

    @Column(name = "store_id")
    private String store_id;

    @Column(name = "order_from")
    private String order_from;

    @Column(name = "order_date")
    private Date order_date = new Date();

    @Column(name = "order_date_time")
    private String order_date_time;

    @Column(name = "enable_delivery")
    private int enable_delivery;

    @Column(name = "net_amount")
    private float net_amount;

    @Column(name = "gross_amount")
    private float gross_amount;

    @Column(name = "payment_mode")
    private String payment_mode;

    @Column(name = "order_type")
    private String order_type;

    @Column(name = "order_instructions")
    private String order_instructions;

    @Column(name = "cgst")
    private float cgst;

    @Column(name = "sgst")
    private float sgst;

    @Column(name = "order_packaging")
    private float order_packaging;

    @Column(name = "order_packaging_cgst")
    private float order_packaging_cgst;

    @Column(name = "order_packaging_sgst")
    private float order_packaging_sgst;

    @Column(name = "order_packaging_cgst_percent")
    private float order_packaging_cgst_percent;

    @Column(name = "order_packaging_sgst_percent")
    private float order_packaging_sgst_percent;

    @Column(name = "discount")
    private float discount;

    @Column(name = "delivery_charges")
    private float delivery_charges;

    @Column(name = "customer_name")
    private String customer_name;

    @Column(name = "phone_number")
    private String phone_number;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "delivery_area")
    private String delivery_area;

    @Column(name = "address_instructions")
    private String address_instructions;

    @Column(name = "Created_by")
    private String createdBy = WERA;

    @Column(name = "Created_date")
    private Date createdDate = new Date();

    @Column(name = "Updated_by")
    private String updatedBy = WERA;

    @Column(name = "Updated_date")
    private Date updatedDate = new Date();


}
