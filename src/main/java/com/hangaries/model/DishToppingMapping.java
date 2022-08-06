package com.hangaries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "DISH_TOPPING_MAPPING")
public class DishToppingMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "sub_product_id")
    private String subProductId;

    @Column(name = "restaurant_id")
    private String restaurantId;

    @Column(name = "store_id")
    private String storeId;

    @Column(name = "ingredient_available_flag")
    private String ingredientAvailableFlag;


}
