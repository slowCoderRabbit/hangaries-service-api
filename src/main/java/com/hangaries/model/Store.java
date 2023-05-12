package com.hangaries.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

import static com.hangaries.constants.HangariesConstants.SYSTEM;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "STORE_MASTER")
@IdClass(StoreKey.class)
public class Store implements Serializable {

    @Id
    @Column(name = "store_Id")
    private String storeId;

    @Id
    @Column(name = "restaurant_id")
    private @NotBlank String restaurantId;

    @Column(name = "resturant_name")
    private @NotBlank String resturantName;

    @Column(name = "country")
    private @NotBlank String country;

    @Column(name = "city")
    private @NotBlank String city;

    @Column(name = "store_active_flag")
    private @NotBlank String storeActiveFlag = "Y";

    @Column(name = "store_kot_print_flag")
    private String storeKOTPrintFlag;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "store_start_time")
    private String storeStartTime;

    @Column(name = "store_end_time")
    private String storeEndTime;

    @Column(name = "store_gst_number")
    private String storeGstNumber;

    @Column(name = "Address_1")
    private String address1;

    @Column(name = "Address_2")
    private String address2;

    @Column(name = "Address_3")
    private String address3;

    @Column(name = "store_email_id")
    private String storeEmailId;

    @Column(name = "store_available_for_pickup")
    private String storeAvailableForPickup;

    @Column(name = "store_available_for_delivery")
    private String storeAvailableForDelivery;

    @Column(name = "wera_merchant_id")
    private String weraMerchantId;

    @Column(name = "wera_api_key")
    private String weraAPIKey;

    @Column(name = "wera_api_value")
    private String weraAPIValue;

    @Column(name = "menu_clone_flag")
    private String menuCloneFlag;

    @Column(name = "online_available_flag")
    private String onlineAvailableFlag;

    @Column(name = "dinein_available_flag")
    private String dineinAvailableFlag;

    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "Created_by")
    private String createdBy = SYSTEM;

    @Column(name = "created_date")
    private Date createdDate = new Date();

    @Column(name = "updated_by")
    private String updatedBy = SYSTEM;

    @Column(name = "updated_date")
    private Date updatedDate = new Date();


}
