package com.hangaries.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@ToString
@Table(name = "MENU_INGREDIENT_MASTER")
public class MenuIngrident {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "store_Id")
    private String storeId;
    @Column(name = "restaurant_id")
    private String restaurantId;
    @Column(name = "sub_product_id")
    private String subProductId;
    @Column(name = "ingredient_type")
    private String ingredientType;
    @Column(name = "price")
    private double price;
    @Column(name = "category")
    private String category;
    @Column(name = "image_path")
    private String imagePath;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "updated_by")
    private String updatedBy;
    @Column(name = "updated_date")
    private Date updatedDate;
    @Column(name = "size")
    private String size;

}