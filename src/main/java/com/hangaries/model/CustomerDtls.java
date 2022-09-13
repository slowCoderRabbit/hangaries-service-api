package com.hangaries.model;

import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

import static com.hangaries.constants.HangariesConstants.SYSTEM;

@Entity
@ToString
@Table(name = "CUSTOMER_ADDRESS_DETAILS")
public class CustomerDtls {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;
    @Column(name = "mobile_number")
    private String mobileNumber;
    @Column(name = "customer_address_type")
    private String customerAddressType;
    @Column(name = "address_1")
    private String address1;
    @Column(name = "address_2")
    private String address2;
    @Column(name = "landmark")
    private String landmark;
    @Column(name = "state")
    private String state;
    @Column(name = "city")
    private String city;
    @Column(name = "zip_code")
    private int zipCode;
    @Column(name = "created_by")
    private String createdBy = SYSTEM;
    @Column(name = "created_date")
    private Date createdDate = new Date();
    @Column(name = "updated_by")
    private String updatedBy = SYSTEM;
    @Column(name = "updated_date")
    private Date updatedDate = new Date();
    @Column(name = "active")
    private String active = "Y";

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getCustomerAddressType() {
        return customerAddressType;
    }

    public void setCustomerAddressType(String customerAddressType) {
        this.customerAddressType = customerAddressType;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
