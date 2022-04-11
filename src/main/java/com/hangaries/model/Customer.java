package com.hangaries.model;



import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;


//@ToString(exclude = {"id"})
@Entity
@Table(name = "CUSTOMER_MASTER")
public class Customer {

    private @Id
    @GeneratedValue
    long id;

    @Column(name = "customer_first_name")
    private String firstName;

    @Column(name = "customer_middle_name")
    private String middleName;

    @Column(name = "customer_last_name")
    private String lastName;

    @Column(name = "mobile_number", nullable = false)
    @Min(value = 10, message = "Mobile Number should not be less than 10")
    private  String mobileNumber;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "restaurant_id")
    private String restaurantId;

    @Column(name = "store_Id")
    private  String storeId;

    @Column(name = "customer_active_status")
    private String customerActiveStatus;

    @Column(name = "alternative_contact_id")
    private String alternativeContactId;

    @Temporal(TemporalType.DATE)
    @Column(name = "effective_start_date")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "effective_end_date")
    private Date endDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_date")
    private Date updatedDate = new Date();

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getCustomerActiveStatus() {
        return customerActiveStatus;
    }

    public void setCustomerActiveStatus(String customerActiveStatus) {
        this.customerActiveStatus = customerActiveStatus;
    }

    public String getAlternativeContactId() {
        return alternativeContactId;
    }

    public void setAlternativeContactId(String alternativeContactId) {
        this.alternativeContactId = alternativeContactId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
