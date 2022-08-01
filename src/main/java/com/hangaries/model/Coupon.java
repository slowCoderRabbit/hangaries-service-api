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
@Table(name = "COUPON_MASTER")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "restaurant_id")
    private String restaurantId;
    @Column(name = "store_Id")
    private String storeId;
    @Column(name = "coupon_code")
    private String couponCode;
    @Column(name = "discount_percentage")
    private Float discountPercentage;
    @Column(name = "description")
    private String description;
    @Column(name = "effective_start_date")
    private Date effectiveStartDate;
    @Column(name = "effective_end_date")
    private Date effectiveEndDate;
    @Column(name = "active_flag")
    private String activeFlag;
    @Column(name = "Created_by")
    private String createdBy = SYSTEM;
    @Column(name = "Created_date")
    private Date createdDate = new Date();
    @Column(name = "Updated_by")
    private String updatedBy = SYSTEM;
    @Column(name = "Updated_date")
    private Date updatedDate = new Date();
}
