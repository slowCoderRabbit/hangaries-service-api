package com.hangaries.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

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

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "product_id")
    private @NotBlank String productId;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "sub_product_Id")
    private @NotBlank String subProductId;

    @Column(name = "quantity")
    private @NotNull Integer quantity;

    @Column(name = "price")
    private Float price;

    @Column(name = "remarks")
    private String remarks;

}
