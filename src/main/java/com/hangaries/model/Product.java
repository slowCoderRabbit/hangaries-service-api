package com.hangaries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    @Column(name = "dish_type")
    private String dishType;

    @Column(name = "product_size")
    private String productSize;

}
