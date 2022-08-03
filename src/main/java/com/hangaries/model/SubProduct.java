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
@Table(name = "SUB_PRODUCT_MASTER")
public class SubProduct {

    @Id
    @Column(name = "sub_product_id")
    private String subProductId;

    @Column(name = "id")
    private int id;

    @Column(name = "ingredient_type")
    private String ingredientType;

    @Column(name = "category")
    private String category;

    @Column(name = "size")
    private String size;


}
