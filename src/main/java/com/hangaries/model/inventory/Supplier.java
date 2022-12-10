package com.hangaries.model.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.hangaries.constants.HangariesConstants.SYSTEM;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "SUPPLIER_MASTER")
public class Supplier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private long supplierId;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "store_id")
    private String storeId;

    @Column(name = "supplier_address")
    private String supplierAddress;

    @Column(name = "supplier_city")
    private String supplierCity;

    @Column(name = "supplier_state")
    private String supplierState;

    @Column(name = "supplier_zip_code")
    private int supplierZipCode;

    @Column(name = "supplier_mobile_number")
    private String supplierMobileNumber;

    @Column(name = "supplier_email_id")
    private String supplierEmailId;

    @Column(name = "supplier_fax_number")
    private String supplierFaxNumber;

    @Column(name = "supplier_gst_number")
    private String supplierGstNumber;

    @Column(name = "supplier_tan_number")
    private String supplierTANNumber;

    @Column(name = "supplier_category")
    private String supplierCategory;

    @Column(name = "supplier_status")
    private String supplierStatus;

    @Column(name = "Created_by")
    private String createdBy = SYSTEM;

    @Column(name = "created_date")
    private Date createdDate = new Date();

    @Column(name = "updated_by")
    private String updatedBy = SYSTEM;

    @Column(name = "updated_date")
    private Date updatedDate = new Date();

}
