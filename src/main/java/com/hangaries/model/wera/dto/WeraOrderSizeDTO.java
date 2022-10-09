package com.hangaries.model.wera.dto;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "WERA_ORDER_SIZE_DETAILS")
public class WeraOrderSizeDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "order_id")
    private String order_id;

    @Column(name = "wera_order_id")
    private String wera_order_id;

    @Column(name = "size_id")
    private String size_id;

    @Column(name = "size_name")
    private String size_name;

    @Column(name = "size_price")
    private float size_price;

    @Column(name = "size_cgst")
    private float cgst;

    @Column(name = "size_sgst")
    private float sgst;

    @Column(name = "size_cgst_percent")
    private float cgst_percent;

    @Column(name = "size_sgst_percent")
    private float sgst_percent;
}
