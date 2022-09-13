package com.hangaries.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ORDER_DETAILS")
public class OrderDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "product_id")
    private @NotBlank String productId;

    @Column(name = "sub_product_Id")
    private @NotBlank String subProductId;

    @Column(name = "quantity")
    private @NotNull Integer quantity;

    @Column(name = "price")
    private Float price;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "order_detail_status")
    private String orderDetailStatus;

    @Column(name = "food_packaged_flag")
    private String foodPackagedFlag;


}
