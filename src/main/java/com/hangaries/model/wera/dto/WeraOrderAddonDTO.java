package com.hangaries.model.wera.dto;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "WERA_ORDER_ADDON_DETAILS")
public class WeraOrderAddonDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "order_id")
    private String order_id;

    @Column(name = "wera_order_id")
    private String wera_order_id;

    @Column(name = "addon_id")
    private String addon_id;

    @Column(name = "addon_name")
    private String name;

    @Column(name = "addon_price")
    private float price;

    @Column(name = "addon_cgst")
    private float cgst;

    @Column(name = "addon_sgst")
    private float sgst;

    @Column(name = "addon_cgst_percent")
    private float cgst_percent;

    @Column(name = "addon_sgst_percent")
    private float sgst_percent;
}
