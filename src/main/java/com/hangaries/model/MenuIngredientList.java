package com.hangaries.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "vMenuIngredientList")
public class MenuIngredientList implements Serializable {

    @Id
    @Column(name = "product_id")
    private String productId;

    @Column(name = "dish")
    private String dish;

    @Column(name = "dish_category")
    private String dishCategory;

    @Column(name = "dish_spice_indicater")
    private String dishSpiceIndicatory;

    @Column(name = "dish_type")
    private String dishType;

    @Column(name = "sub_product_id")
    private String subProductId;

    @Column(name = "ingredient_type")
    private String ingredientType;

    @Column(name = "menu_available_flag")
    private String menuAvailableFlag;

    @Column(name = "ingredient_available_flag")
    private String ingredientAvailableFlag;


}
