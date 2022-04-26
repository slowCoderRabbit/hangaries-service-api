package com.hangaries.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.hangaries.config.HangariesConstants.SYSTEM;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ORDER_PROCESSING_DETAILS")
public class OrderProcessingDetails {

    @Id
    @Column(name = "order_id")
    private @NotBlank String orderId;

    @Column(name = "restaurant_id")
    private @NotBlank String restaurantId;

    @Column(name = "store_Id")
    private @NotBlank String storeId;

    @Column(name = "user_id")
    private @NotNull String customerId;

    @Column(name = "role_category")
    private @NotNull String roleCategory;

    @Column(name = "order_status")
    private @NotNull String orderStatus;

    @Column(name = "Created_by")
    private String createdBy = SYSTEM;

    @Column(name = "Created_date")
    private Date createdDate = new Date();

    @Column(name = "Updated_by")
    private String updatedBy = SYSTEM;

    @Column(name = "Updated_date")
    private Date updatedDate = new Date();


}
