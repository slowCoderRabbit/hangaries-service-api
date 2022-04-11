package com.hangaries.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Menu master entity
 */
@Entity
@Table(name="MENU_MASTER")
public class Menu {
    @Id
    @GeneratedValue
    @Column(name="id")
    private long id;
    @Column(name="product_id")
    private String productId;
    @Column(name="restaurant_id")
    private String restaruantId;
    @Column(name="store_id")
    private String storeId;
    @Column(name="section")
    private String section;
    @Column(name="dish")
    private String dish;
    @Column(name="dish_category")
    private String dishCategory;
    @Column(name="dish_spice_indicater")
    private String dishSpiceIndicatory;
    @Column(name="dish_type")
    private String dishType;
    @Column(name="dish_description_id")
    private String dishDescriptionId;
    @Column(name="product_size")
    private String productSize;
    @Column(name="price")
    private Double price;
    @Column(name="image_path")
    private String imagePath;
    @Column(name="menu_available_flag")
    private String menuAvailableFlag;
    @Column(name="Common_image")
    private String commonImage;
    @Column(name="ingredient_exists_flag")
    private String ingredientExistsFalg;
    @Column(name="Created_by")
    private String createdBy;
    @Column(name="Created_date")
    private Date createdDate;
    @Column(name="Updated_by")
    private String updatedBy;
    @Column(name="Updated_date")
    private Date updatedDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getRestaruantId() {
        return restaruantId;
    }

    public void setRestaruantId(String restaruantId) {
        this.restaruantId = restaruantId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public String getDishCategory() {
        return dishCategory;
    }

    public void setDishCategory(String dishCategory) {
        this.dishCategory = dishCategory;
    }

    public String getDishSpiceIndicatory() {
        return dishSpiceIndicatory;
    }

    public void setDishSpiceIndicatory(String dishSpiceIndicatory) {
        this.dishSpiceIndicatory = dishSpiceIndicatory;
    }

    public String getDishType() {
        return dishType;
    }

    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

    public String getDishDescriptionId() {
        return dishDescriptionId;
    }

    public void setDishDescriptionId(String dishDescriptionId) {
        this.dishDescriptionId = dishDescriptionId;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getMenuAvailableFlag() {
        return menuAvailableFlag;
    }

    public void setMenuAvailableFlag(String menuAvailableFlag) {
        this.menuAvailableFlag = menuAvailableFlag;
    }

    public String getCommonImage() {
        return commonImage;
    }

    public void setCommonImage(String commonImage) {
        this.commonImage = commonImage;
    }

    public String getIngredientExistsFalg() {
        return ingredientExistsFalg;
    }

    public void setIngredientExistsFalg(String ingredientExistsFalg) {
        this.ingredientExistsFalg = ingredientExistsFalg;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", productId='" + productId + '\'' +
                ", restaruantId='" + restaruantId + '\'' +
                ", storeId='" + storeId + '\'' +
                ", section='" + section + '\'' +
                ", dish='" + dish + '\'' +
                ", dishCategory='" + dishCategory + '\'' +
                ", dishSpiceIndicatory='" + dishSpiceIndicatory + '\'' +
                ", dishType='" + dishType + '\'' +
                ", dishDescriptionId='" + dishDescriptionId + '\'' +
                ", productSize='" + productSize + '\'' +
                ", price=" + price +
                ", imagePath='" + imagePath + '\'' +
                ", menuAvailableFlag=" + menuAvailableFlag +
                ", commonImage=" + commonImage +
                ", ingredientExistsFalg=" + ingredientExistsFalg +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedDate=" + updatedDate +
                '}';
    }
}
