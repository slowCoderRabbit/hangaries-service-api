package com.hangaries.model.wera.dto;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

import static com.hangaries.constants.HangariesConstants.SYSTEM;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "WERA_ORDER_DETAILS")
public class WeraOrderDetailsDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "order_id")
    private String order_id;

    @Column(name = "wera_order_id")
    private String wera_order_id;

    @Column(name = "wera_item_id")
    private int wera_item_id;

    @Column(name = "item_id")
    private String item_id;

    @Column(name = "item_name")
    private String item_name;

    @Column(name = "item_unit_price")
    private float item_unit_price;

    @Column(name = "subtotal")
    private float subtotal;

    @Column(name = "discount")
    private float discount;

    @Column(name = "item_quantity")
    private int item_quantity;

    @Column(name = "cgst")
    private float cgst;

    @Column(name = "sgst")
    private float sgst;

    @Column(name = "cgst_percent")
    private float cgst_percent;

    @Column(name = "sgst_percent")
    private float sgst_percent;

    @Column(name = "packaging")
    private float packaging;

    @Column(name = "packaging_cgst")
    private float packaging_cgst;

    @Column(name = "packaging_sgst")
    private float packaging_sgst;

    @Column(name = "packaging_cgst_percent")
    private float packaging_cgst_percent;

    @Column(name = "packaging_sgst_percent")
    private float packaging_sgst_percent;

    @Column(name = "Created_by")
    private String createdBy = SYSTEM;

    @Column(name = "Created_date")
    private Date createdDate = new Date();

    @Column(name = "Updated_by")
    private String updatedBy = SYSTEM;

    @Column(name = "Updated_date")
    private Date updatedDate = new Date();


}
