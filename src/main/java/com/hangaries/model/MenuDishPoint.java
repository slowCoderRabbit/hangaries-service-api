package com.hangaries.model;

import javax.persistence.*;

@Entity
@Table(name="MENU_OPTION_MASTER")
public class MenuDishPoint{
    @Id
    @GeneratedValue
    private long id;
    @Column(name="Dish_description_ID")
    private String dishDescriptionId;
    @Column(name="Description")
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDishDescriptionId() {
        return dishDescriptionId;
    }

    public void setDishDescriptionId(String dishDescriptionId) {
        this.dishDescriptionId = dishDescriptionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MenuDishPoint{" +
                "id=" + id +
                ", dishDescriptionId='" + dishDescriptionId + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}