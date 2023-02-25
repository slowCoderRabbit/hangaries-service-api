package com.hangaries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReportCustomerData {

    @Column(name = "user_login_id")
    private String userLoginId;

    @Column(name = "restaurant_id")
    private String restaurantId;

    @Column(name = "store_id")
    private String storeId;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "address_type")
    private String addressType;

    @Column(name = "customer_address")
    private String customerAddress;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "active_remarks")
    private String activeRemarks;

    @Column(name = "created_date")
    private Date createdDate;


}
