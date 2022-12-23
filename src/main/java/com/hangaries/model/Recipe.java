package com.hangaries.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

import static com.hangaries.constants.HangariesConstants.SYSTEM;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "RECIPE_MASTER")
public class Recipe {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "restaurant_id")
    private String restaurantId;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "item_id")
    private long itemId;

    @Column(name = "item_qty")
    private Float itemQty;

    @Column(name = "item_uom")
    private String itemUom;

    @Column(name = "item_cost")
    private Float itemCost;

    @Column(name = "item_status")
    private String itemStatus;

    @Column(name = "Created_by")
    private String createdBy = SYSTEM;

    @Column(name = "Created_date")
    private Date createdDate = new Date();

    @Column(name = "Updated_by")
    private String updatedBy = SYSTEM;

    @Column(name = "Updated_date")
    private Date updatedDate = new Date();

}
