package com.hangaries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "USER_LOGIN_DETAILS")
public class UserLoginDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "restaurant_id")
    private String restaurantId;

    @Column(name = "store_Id")
    private String storeId;

    @Column(name = "user_login_id")
    private String userLoginId;

    @Column(name = "login_time")
    private Date loginTime;

    @Column(name = "logout_time")
    private Date logoutTime;

    @Column(name = "login_status")
    private String loginStatus;

}
