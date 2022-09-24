package com.hangaries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

import static com.hangaries.constants.HangariesConstants.SYSTEM;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "BUSINESS_DATE_MASTER")
public class BusinessDate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "restaurant_id")
    private String restaurantId;
    @Column(name = "store_Id")
    private String storeId;
    @Column(name = "previous_business_date")
    private Date previousBusinessDate;
    @Column(name = "business_date")
    private Date businessDate;
    @Column(name = "next_business_date")
    private Date nextBusinessDate;
    @Column(name = "created_by")
    private String createdBy = SYSTEM;
    @Column(name = "created_date")
    private Date createdDate = new Date();
    @Column(name = "updated_by")
    private String updatedBy = SYSTEM;
    @Column(name = "updated_date")
    private Date updatedDate = new Date();
    @Transient
    private String result;

}
