package com.hangaries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

import static com.hangaries.constants.HangariesConstants.SYSTEM;

/**
 * Menu master entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "MENU_MASTER")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "product_id")
    private String productId;
    @Column(name = "restaurant_id")
    private String restaruantId;
    @Column(name = "store_id")
    private String storeId;
    @Column(name = "section")
    private String section;
    @Column(name = "dish")
    private String dish;
    @Column(name = "dish_category")
    private String dishCategory;
    @Column(name = "dish_spice_indicater")
    private String dishSpiceIndicatory;
    @Column(name = "dish_type")
    private String dishType;
    @Column(name = "dish_description_id")
    private String dishDescriptionId;
    @Column(name = "product_size")
    private String productSize;
    @Column(name = "price")
    private Double price;
    @Column(name = "image_path")
    private String imagePath;
    @Column(name = "menu_available_flag")
    private String menuAvailableFlag;
    @Column(name = "Common_image")
    private String commonImage;
    @Column(name = "ingredient_exists_flag")
    private String ingredientExistsFalg;
    @Column(name = "online_applicable_flag")
    private String onlineApplicableFlag;
    @Column(name = "kds_routing_name")
    private String kdsRoutingName;
    @Column(name = "Created_by")
    private String createdBy = SYSTEM;
    @Column(name = "Created_date")
    private Date createdDate = new Date();
    @Column(name = "Updated_by")
    private String updatedBy = SYSTEM;
    @Column(name = "Updated_date")
    private Date updatedDate = new Date();

}
