package com.hangaries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "APP_DETAILS")
@IdClass(AppDetailsKey.class)
public class AppDetails implements Serializable {

    @Id
    @Column(name = "app_environment")
    private String appEnvironment;

    @Id
    @Column(name = "restaurant_id")
    private String restaurantId;

    @Id
    @Column(name = "store_Id")
    private String storeId;

    @Column(name = "app_version")
    private String appVersion;

}
