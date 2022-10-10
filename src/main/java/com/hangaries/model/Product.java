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
@Table(name = "PRODUCT_MASTER")
public class Product {

    @Id
    @Column(name = "product_id")
    private String productId;

    @Column(name = "id")
    private int id;

    @Column(name = "section")
    private String section;

    @Column(name = "dish")
    private String dish;

    @Column(name = "dish_category")
    private String dishCategory;

    @Column(name = "dish_spice_indicator")
    private String dishSpiceIndicator;

    @Column(name = "dish_type")
    private String dishType;

    @Column(name = "dish_description_id")
    private String dishDescriptionId;

    @Column(name = "product_size")
    private String productSize;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "common_image")
    private String commonImage;

    @Column(name = "price")
    private float price;

    @Column(name = "menu_available_flag")
    private String menuAvailableFlag;

    @Column(name = "ingredient_exist_flag")
    private String ingredientExistFlag;

    @Column(name = "kds_routing_name")
    private String kdsRoutingName;

    @Column(name = "product_status")
    private String productStatus;

    @Column(name = "Created_by")
    private String createdBy = SYSTEM;
    @Column(name = "Created_date")
    private Date createdDate = new Date();
    @Column(name = "Updated_by")
    private String updatedBy = SYSTEM;
    @Column(name = "Updated_date")
    private Date updatedDate = new Date();

}
