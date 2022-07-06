package com.hangaries.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.hangaries.constants.HangariesConstants.SYSTEM;

@Data
@NoArgsConstructor
@ToString
@Entity
@Table(name = "USER_MASTER")
public class User {

    @Id
    @Column(name = "user_seq_no")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userSeqNo;

    @Column(name = "user_first_name")
    private @NotBlank String firstName;

    @Column(name = "user_middle_name")
    private String middleName;

    @Column(name = "user_last_name")
    private String lastName;

    @Column(name = "user_login_id")
    private @NotBlank String loginId;

    @Column(name = "user_dob")
    private @NotNull Date userDob;

    @Column(name = "user_login_password")
    private String loginPassword = "default";

    @Column(name = "restaurant_id")
    private @NotBlank String restaurantId;

    @Column(name = "store_Id")
    private @NotBlank String storeId;

    @Column(name = "status")
    private String status;

    @Column(name = "adhaar_id")
    private String adhaarId;

    @Column(name = "pan_id")
    private String panId;

    @Column(name = "driving_license_number")
    private String drivingLicenseNumber;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "address_3")
    private String address3;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "zip_code")
    private Integer zipCode;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "alternative_contact_id")
    private String alternativeContactId;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "photo_file_location")
    private String photoFileLocation;

    @Column(name = "role_category")
    private String roleCategory;

    @Column(name = "effective_start_date")
    private Date effectiveStartDate = new Date();

    @Column(name = "effective_end_date")
    private Date effectiveEndDate = new Date();

    @Column(name = "created_by")
    private String createdBy = SYSTEM;

    @Column(name = "created_date")
    private Date createdDate = new Date();

    @Column(name = "updated_by")
    private String updatedBy = SYSTEM;

    @Column(name = "updated_date")
    private Date updatedDate = new Date();


}
