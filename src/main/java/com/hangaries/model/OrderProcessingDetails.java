package com.hangaries.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.hangaries.config.HangariesConstants.SYSTEM;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@IdClass(OrderProcessingDetailsId.class)
@Table(name = "ORDER_PROCESSING_DETAILS")
public class OrderProcessingDetails {

    @Id
    @Column(name = "order_id")
    private @NotBlank String orderId;

    @Id
    @Column(name = "restaurant_id")
    private @NotBlank String restaurantId;

    @Id
    @Column(name = "store_Id")
    private @NotBlank String storeId;

    @Id
    @Column(name = "order_status")
    private @NotNull String orderStatus;

    @Column(name = "user_seq_no")
    private @NotNull Integer userSeqNo;

    @Column(name = "role_category")
    private @NotNull String roleCategory;

    @Column(name = "Created_by")
    private String createdBy = SYSTEM;

    @Column(name = "Created_date")
    private Date createdDate = new Date();

    @Column(name = "Updated_by")
    private String updatedBy = SYSTEM;

    @Column(name = "Updated_date")
    private Date updatedDate = new Date();


}
