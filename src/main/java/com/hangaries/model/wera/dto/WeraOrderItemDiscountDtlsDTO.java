package com.hangaries.model.wera.dto;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "WERA_ORDER_ITEM_DISCOUNT_DTLS")
public class WeraOrderItemDiscountDtlsDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "order_id")
    private String order_id;

    @Column(name = "wera_order_id")
    private String wera_order_id;

    @Column(name = "item_discount_type")
    private String item_discount_type;

    @Column(name = "item_discount_amount")
    private float item_discount_amount;

}
