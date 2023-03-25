package com.hangaries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

import static com.hangaries.constants.HangariesConstants.SYSTEM;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "SUB_PRODUCT_MASTER")
public class SubProduct {

    @Id
    @Column(name = "sub_product_id")
    private String subProductId;

    @Column(name = "id")
    private int id;

    @Column(name = "restaurant_id")
    private String restaurantId;

    @Column(name = "store_id")
    private String storeId;

    @Column(name = "ingredient_type")
    private String ingredientType;

    @Column(name = "category")
    private String category;

    @Column(name = "size")
    private String size;

    @Column(name = "price")
    private float price;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "sub_product_status")
    private String subProductStatus;

    @Column(name = "Created_by")
    private String createdBy = SYSTEM;

    @Column(name = "Created_date")
    private Date createdDate = new Date();

    @Column(name = "Updated_by")
    private String updatedBy = SYSTEM;

    @Column(name = "Updated_date")
    private Date updatedDate = new Date();


}
